/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.resolve.transformers

import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.kotlin.KtFakeSourceElementKind
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.PrivateForInline
import org.jetbrains.kotlin.fir.copyWithNewSourceKind
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.declarations.utils.isFromVararg
import org.jetbrains.kotlin.fir.expressions.*
import org.jetbrains.kotlin.fir.resolve.ScopeSession
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguouslyResolvedAnnotationFromPlugin
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeCyclicTypeBound
import org.jetbrains.kotlin.fir.resolve.lookupSuperTypes
import org.jetbrains.kotlin.fir.scopes.FirScope
import org.jetbrains.kotlin.fir.scopes.createImportingScopes
import org.jetbrains.kotlin.fir.scopes.getNestedClassifierScope
import org.jetbrains.kotlin.fir.scopes.impl.FirMemberTypeParameterScope
import org.jetbrains.kotlin.fir.scopes.impl.nestedClassifierScope
import org.jetbrains.kotlin.fir.scopes.impl.wrapNestedClassifierScopeWithSubstitutionForSuperType
import org.jetbrains.kotlin.fir.types.*
import org.jetbrains.kotlin.fir.types.builder.buildErrorTypeRef
import org.jetbrains.kotlin.fir.visitors.transformSingle
import org.jetbrains.kotlin.fir.whileAnalysing
import org.jetbrains.kotlin.utils.addToStdlib.shouldNotBeCalled

class FirTypeResolveProcessor(
    session: FirSession,
    scopeSession: ScopeSession
) : FirTransformerBasedResolveProcessor(session, scopeSession, FirResolvePhase.TYPES) {
    override val transformer = FirTypeResolveTransformer(session, scopeSession)
}

fun <F : FirClassLikeDeclaration> F.runTypeResolvePhaseForLocalClass(
    session: FirSession,
    scopeSession: ScopeSession,
    currentScopeList: List<FirScope>,
    useSiteFile: FirFile,
    containingDeclarations: List<FirDeclaration>,
): F {
    val transformer = FirTypeResolveTransformer(
        session,
        scopeSession,
        currentScopeList,
        initialCurrentFile = useSiteFile,
        classDeclarationsStack = containingDeclarations.filterIsInstanceTo(ArrayDeque())
    )

    return this.transform(transformer, null)
}

@OptIn(PrivateForInline::class)
open class FirTypeResolveTransformer(
    final override val session: FirSession,
    @property:PrivateForInline val scopeSession: ScopeSession,
    initialScopes: List<FirScope> = emptyList(),
    initialCurrentFile: FirFile? = null,
    @property:PrivateForInline val classDeclarationsStack: ArrayDeque<FirClass> = ArrayDeque()
) : FirAbstractTreeTransformer<Any?>(FirResolvePhase.TYPES) {
    @PrivateForInline
    val scopes = mutableListOf<FirScope>()

    @PrivateForInline
    val towerScope = scopes.asReversed()

    private var currentDeclaration: FirDeclaration? = null

    private inline fun <T> withDeclaration(declaration: FirDeclaration, crossinline action: () -> T): T {
        val oldDeclaration = currentDeclaration
        return try {
            currentDeclaration = declaration
            action()
        } finally {
            currentDeclaration = oldDeclaration
        }
    }

    init {
        scopes.addAll(initialScopes.asReversed())
    }

    private val typeResolverTransformer: FirSpecificTypeResolverTransformer = FirSpecificTypeResolverTransformer(session)

    @PrivateForInline
    var currentFile: FirFile? = initialCurrentFile

    override fun transformFile(file: FirFile, data: Any?): FirFile {
        checkSessionConsistency(file)
        return withFileScope(file) {
            super.transformFile(file, data)
        }
    }

    inline fun <R> withFileScope(file: FirFile, crossinline action: () -> R): R {
        currentFile = file
        return withScopeCleanup {
            scopes.addAll(createImportingScopes(file, session, scopeSession))
            action()
        }
    }

    override fun transformRegularClass(regularClass: FirRegularClass, data: Any?): FirStatement {
        whileAnalysing(session, regularClass) {
            withClassDeclarationCleanup(regularClass) {
                transformClassTypeParameters(regularClass, data)
                return resolveClassContent(regularClass, data)
            }
        }
    }

    fun transformClassTypeParameters(regularClass: FirRegularClass, data: Any?) {
        withScopeCleanup {
            addTypeParametersScope(regularClass)
            regularClass.typeParameters.forEach {
                it.accept(this, data)
            }
            unboundCyclesInTypeParametersSupertypes(regularClass)
        }
    }

    inline fun <R> withClassDeclarationCleanup(regularClass: FirRegularClass, action: () -> R): R {
        return withClassDeclarationCleanup(classDeclarationsStack, regularClass, action)
    }

    override fun transformAnonymousObject(anonymousObject: FirAnonymousObject, data: Any?): FirStatement {
        withClassDeclarationCleanup(classDeclarationsStack, anonymousObject) {
            return resolveClassContent(anonymousObject, data)
        }
    }

    override fun transformConstructor(constructor: FirConstructor, data: Any?): FirConstructor = whileAnalysing(session, constructor) {
        return withScopeCleanup {
            addTypeParametersScope(constructor)
            transformDeclaration(constructor, data)
        } as FirConstructor
    }

    override fun transformTypeAlias(typeAlias: FirTypeAlias, data: Any?): FirTypeAlias = whileAnalysing(session, typeAlias) {
        withScopeCleanup {
            addTypeParametersScope(typeAlias)
            transformDeclaration(typeAlias, data)
        } as FirTypeAlias
    }

    override fun transformEnumEntry(enumEntry: FirEnumEntry, data: Any?): FirEnumEntry = whileAnalysing(session, enumEntry) {
        enumEntry.transformReturnTypeRef(this, data)
        enumEntry.transformTypeParameters(this, data)
        enumEntry.transformAnnotations(this, data)
        enumEntry
    }

    override fun transformReceiverParameter(receiverParameter: FirReceiverParameter, data: Any?): FirReceiverParameter {
        return receiverParameter.transformAnnotations(this, data).transformTypeRef(this, data)
    }

    override fun transformProperty(property: FirProperty, data: Any?): FirProperty = whileAnalysing(session, property) {
        withScopeCleanup {
            withDeclaration(property) {
                addTypeParametersScope(property)
                property.transformTypeParameters(this, data)
                    .transformReturnTypeRef(this, data)
                    .transformReceiverParameter(this, data)
                    .transformContextReceivers(this, data)
                    .transformGetter(this, data)
                    .transformSetter(this, data)
                    .transformBackingField(this, data)
                    .transformAnnotations(this, data)

                if (property.isFromVararg == true) {
                    property.transformTypeToArrayType()
                    property.backingField?.transformTypeToArrayType()
                    setAccessorTypesByPropertyType(property)
                }

                when {
                    property.returnTypeRef is FirResolvedTypeRef && property.delegate != null -> {
                        setAccessorTypesByPropertyType(property)
                    }
                    property.returnTypeRef !is FirResolvedTypeRef && property.initializer == null &&
                            property.getter?.returnTypeRef is FirResolvedTypeRef -> {
                        val returnTypeRef = property.getter!!.returnTypeRef

                        property.replaceReturnTypeRef(returnTypeRef.copyWithNewSourceKind(KtFakeSourceElementKind.PropertyTypeFromGetterReturnType))
                        property.backingField?.replaceReturnTypeRef(
                            returnTypeRef.copyWithNewSourceKind(KtFakeSourceElementKind.PropertyTypeFromGetterReturnType)
                        )

                        property.setter?.valueParameters?.forEach {
                            it.replaceReturnTypeRef(
                                returnTypeRef.copyWithNewSourceKind(KtFakeSourceElementKind.PropertyTypeFromGetterReturnType)
                            )
                        }
                    }
                }

                unboundCyclesInTypeParametersSupertypes(property)

                property
            }
        }
    }

    private fun setAccessorTypesByPropertyType(property: FirProperty) {
        property.getter?.replaceReturnTypeRef(property.returnTypeRef)
        property.setter?.valueParameters?.map { it.replaceReturnTypeRef(property.returnTypeRef) }
    }

    override fun transformField(field: FirField, data: Any?): FirField = whileAnalysing(session, field) {
        withScopeCleanup {
            field.transformReturnTypeRef(this, data).transformAnnotations(this, data)
            field
        }
    }

    override fun transformSimpleFunction(
        simpleFunction: FirSimpleFunction,
        data: Any?,
    ): FirSimpleFunction = whileAnalysing(session, simpleFunction) {
        withScopeCleanup {
            withDeclaration(simpleFunction) {
                addTypeParametersScope(simpleFunction)
                transformDeclaration(simpleFunction, data).also {
                    unboundCyclesInTypeParametersSupertypes(it as FirTypeParametersOwner)
                }
            }
        } as FirSimpleFunction
    }

    private fun unboundCyclesInTypeParametersSupertypes(typeParametersOwner: FirTypeParameterRefsOwner) {
        for (typeParameter in typeParametersOwner.typeParameters) {
            if (typeParameter !is FirTypeParameter) continue
            if (hasSupertypePathToParameter(typeParameter, typeParameter, mutableSetOf())) {
                val errorType = buildErrorTypeRef {
                    diagnostic = ConeCyclicTypeBound(typeParameter.symbol, typeParameter.bounds.toImmutableList())
                }
                typeParameter.replaceBounds(
                    listOf(errorType)
                )
            }
        }
    }

    private fun hasSupertypePathToParameter(
        currentTypeParameter: FirTypeParameter,
        typeParameter: FirTypeParameter,
        visited: MutableSet<FirTypeParameter>
    ): Boolean {
        if (visited.isNotEmpty() && currentTypeParameter == typeParameter) return true
        if (!visited.add(currentTypeParameter)) return false

        return currentTypeParameter.bounds.any {
            val nextTypeParameter = it.coneTypeSafe<ConeTypeParameterType>()?.lookupTag?.typeParameterSymbol?.fir ?: return@any false

            hasSupertypePathToParameter(nextTypeParameter, typeParameter, visited)
        }
    }

    override fun transformImplicitTypeRef(implicitTypeRef: FirImplicitTypeRef, data: Any?): FirTypeRef {
        return implicitTypeRef
    }

    override fun transformTypeRef(typeRef: FirTypeRef, data: Any?): FirResolvedTypeRef {
        return typeResolverTransformer.withFile(currentFile) {
            typeRef.transform(
                typeResolverTransformer,
                ScopeClassDeclaration(towerScope, classDeclarationsStack, containerDeclaration = currentDeclaration)
            )
        }
    }

    override fun transformValueParameter(
        valueParameter: FirValueParameter,
        data: Any?,
    ): FirStatement = whileAnalysing(session, valueParameter) {
        withDeclaration(valueParameter) {
            valueParameter.transformReturnTypeRef(this, data)
            valueParameter.transformAnnotations(this, data)
            valueParameter.transformVarargTypeToArrayType()
            valueParameter
        }
    }

    override fun transformBlock(block: FirBlock, data: Any?): FirStatement {
        return block
    }

    override fun transformAnnotation(annotation: FirAnnotation, data: Any?): FirStatement {
        shouldNotBeCalled()
    }

    override fun transformAnnotationCall(
        annotationCall: FirAnnotationCall,
        data: Any?
    ): FirStatement = whileAnalysing(session, annotationCall) {
        when (val originalTypeRef = annotationCall.annotationTypeRef) {
            is FirResolvedTypeRef -> {
                when (annotationCall.annotationResolvePhase) {
                    FirAnnotationResolvePhase.Unresolved -> when (originalTypeRef) {
                        is FirErrorTypeRef -> return annotationCall.also { it.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.Types) }
                        else -> shouldNotBeCalled()
                    }
                    FirAnnotationResolvePhase.CompilerRequiredAnnotations -> {
                        annotationCall.transformTypeArguments(this, data)
                        annotationCall.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.Types)
                        val alternativeResolvedTypeRef =
                            originalTypeRef.delegatedTypeRef?.transformSingle(this, data) ?: return annotationCall
                        val coneTypeFromCompilerRequiredPhase = originalTypeRef.coneType
                        val coneTypeFromTypesPhase = alternativeResolvedTypeRef.coneType
                        if (coneTypeFromTypesPhase != coneTypeFromCompilerRequiredPhase) {
                            val errorTypeRef = buildErrorTypeRef {
                                source = originalTypeRef.source
                                type = coneTypeFromCompilerRequiredPhase
                                delegatedTypeRef = originalTypeRef.delegatedTypeRef
                                diagnostic = ConeAmbiguouslyResolvedAnnotationFromPlugin(
                                    coneTypeFromCompilerRequiredPhase,
                                    coneTypeFromTypesPhase
                                )
                            }
                            annotationCall.replaceAnnotationTypeRef(errorTypeRef)
                        }
                    }
                    FirAnnotationResolvePhase.Types -> {}
                }
            }
            else -> {
                val transformedTypeRef = originalTypeRef.transformSingle(this, data)
                annotationCall.transformTypeArguments(this, data)
                annotationCall.replaceAnnotationResolvePhase(FirAnnotationResolvePhase.Types)
                annotationCall.replaceAnnotationTypeRef(transformedTypeRef)
            }
        }
        return annotationCall
    }

    inline fun <T> withScopeCleanup(crossinline l: () -> T): T {
        val sizeBefore = scopes.size
        val result = l()
        val size = scopes.size
        assert(size >= sizeBefore)
        repeat(size - sizeBefore) {
            scopes.removeAt(scopes.lastIndex)
        }
        return result
    }

    private fun resolveClassContent(
        firClass: FirClass,
        data: Any?
    ): FirStatement {

        withScopeCleanup {
            firClass.transformAnnotations(this, null)

            if (firClass is FirRegularClass) {
                addTypeParametersScope(firClass)
            }

            // ConstructedTypeRef should be resolved only with type parameters, but not with nested classes and classes from supertypes
            for (constructor in firClass.declarations.filterIsInstance<FirConstructor>()) {
                transformDelegatedConstructorCall(constructor)
            }

        }

        return withClassScopes(firClass) {
            // Note that annotations are still visited here
            // again, although there's no need in it
            transformElement(firClass, data)
        }
    }

    fun transformDelegatedConstructorCall(constructor: FirConstructor) {
        constructor.delegatedConstructor?.let(this::resolveConstructedTypeRefForDelegatedConstructorCall)
    }

    inline fun <R> withClassScopes(firClass: FirClass, crossinline action: () -> R): R {
        return withScopeCleanup {
            // ? Is it Ok to use original file session here ?
            val superTypes = lookupSuperTypes(
                firClass,
                lookupInterfaces = false,
                deep = true,
                substituteTypes = true,
                useSiteSession = session
            ).asReversed()
            for (superType in superTypes) {
                superType.lookupTag.getNestedClassifierScope(session, scopeSession)?.let { nestedClassifierScope ->
                    val scope = nestedClassifierScope.wrapNestedClassifierScopeWithSubstitutionForSuperType(superType, session)
                    scopes.add(scope)
                }
            }
            session.nestedClassifierScope(firClass)?.let(scopes::add)
            if (firClass is FirRegularClass) {
                val companionObject = firClass.companionObjectSymbol?.fir
                if (companionObject != null) {
                    session.nestedClassifierScope(companionObject)?.let(scopes::add)
                }
                addTypeParametersScope(firClass)
            }

            action()
        }
    }

    private fun resolveConstructedTypeRefForDelegatedConstructorCall(
        delegatedConstructorCall: FirDelegatedConstructorCall
    ) {
        delegatedConstructorCall.replaceConstructedTypeRef(
            delegatedConstructorCall.constructedTypeRef.transform<FirTypeRef, Any?>(this, null)
        )

        delegatedConstructorCall.transformCalleeReference(this, null)
    }

    fun addTypeParametersScope(firMemberDeclaration: FirMemberDeclaration) {
        if (firMemberDeclaration.typeParameters.isNotEmpty()) {
            scopes.add(FirMemberTypeParameterScope(firMemberDeclaration))
        }
    }
}
