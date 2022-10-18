/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.symbols

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.analysis.api.*
import org.jetbrains.kotlin.analysis.api.annotations.*
import org.jetbrains.kotlin.analysis.api.base.KtContextReceiver
import org.jetbrains.kotlin.analysis.api.components.KtSymbolContainingDeclarationProviderMixIn
import org.jetbrains.kotlin.analysis.api.components.KtSymbolInfoProviderMixIn
import org.jetbrains.kotlin.analysis.api.symbols.markers.KtNamedSymbol
import org.jetbrains.kotlin.analysis.api.symbols.markers.KtPossiblyNamedSymbol
import org.jetbrains.kotlin.analysis.api.types.KtClassErrorType
import org.jetbrains.kotlin.analysis.api.types.KtType
import org.jetbrains.kotlin.analysis.project.structure.KtModule
import org.jetbrains.kotlin.analysis.utils.printer.PrettyPrinter
import org.jetbrains.kotlin.analysis.utils.printer.prettyPrint
import org.jetbrains.kotlin.descriptors.Visibility
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.name.SpecialNames
import org.jetbrains.kotlin.renderer.render
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.full.*

public class DebugSymbolRenderer(
    public val renderExtra: Boolean = false,
) {

    context(KtAnalysisSession)
    public fun render(symbol: KtSymbol): String = prettyPrint { renderSymbol(symbol) }

    context(KtAnalysisSession)
    public fun renderAnnotationApplication(application: KtAnnotationApplication): String =
        prettyPrint { renderAnnotationApplication(application) }

    context(KtAnalysisSession)
    public fun renderType(type: KtType): String = prettyPrint { renderType(type) }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderSymbol(symbol: KtSymbol) {
        renderSymbolInternals(symbol)

        if (!renderExtra) return
        withIndent {
            @Suppress("DEPRECATION")
            (symbol as? KtCallableSymbol)?.getDispatchReceiverType()?.let { dispatchType ->
                appendLine().append("getDispatchReceiver()").append(": ")
                renderType(dispatchType)
            }

            KtSymbolContainingDeclarationProviderMixIn::class
                .declaredMemberExtensionFunctions
                .filter { it.name == "getContainingModule" }
                .forEach {
                    renderFunction(it, renderSymbolsFully = false, this@KtAnalysisSession, symbol)
                }

            KtSymbolInfoProviderMixIn::class.declaredMemberExtensionProperties
                .asSequence()
                .filter { (it.extensionReceiverParameter?.type?.classifier as? KClass<*>)?.isInstance(symbol) == true }
                .forEach { renderProperty(it, renderSymbolsFully = false, this@KtAnalysisSession, symbol) }
        }
    }

    public fun KtAnalysisSession.renderForSubstitutionOverrideUnwrappingTest(symbol: KtSymbol): String = prettyPrint {
        if (symbol !is KtCallableSymbol) return@prettyPrint

        renderSymbolHeader(symbol)

        withIndent {
            renderProperty(KtCallableSymbol::callableIdIfNonLocal, renderSymbolsFully = false, symbol)
            if (symbol is KtNamedSymbol) {
                renderProperty(KtNamedSymbol::name, renderSymbolsFully = false, symbol)
            }
            renderProperty(KtCallableSymbol::origin, renderSymbolsFully = false, symbol)
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderFunction(function: KFunction<*>, renderSymbolsFully: Boolean, vararg args: Any) {
        appendLine().append(function.name).append(": ")
        renderFunctionCall(function, renderSymbolsFully, args)
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderProperty(property: KProperty<*>, renderSymbolsFully: Boolean, vararg args: Any) {
        appendLine().append(property.name).append(": ")
        renderFunctionCall(property.getter, renderSymbolsFully, args)
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderFunctionCall(function: KFunction<*>, renderSymbolsFully: Boolean, args: Array<out Any>) {
        try {
            renderValue(function.call(*args), renderSymbolsFully)
        } catch (e: InvocationTargetException) {
            append("Could not render due to ").appendLine(e.cause.toString())
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderSymbolInternals(symbol: KtSymbol, customIgnoredPropertynames: List<String> = emptyList()) {
        renderSymbolHeader(symbol)
        val apiClass = getSymbolApiClass(symbol)
        withIndent {
            apiClass.members
                .asSequence()
                .filterIsInstance<KProperty<*>>()
                .filter { it.name !in ignoredPropertyNames && it.name !in customIgnoredPropertynames }
                .sortedBy { it.name }
                .forEach { member ->
                    renderProperty(
                        member,
                        renderSymbolsFully = member.name == KtValueParameterSymbol::generatedPrimaryConstructorProperty.name,
                        symbol
                    )
                }
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderSymbolHeader(symbol: KtSymbol) {
        val apiClass = getSymbolApiClass(symbol)
        append(apiClass.simpleName).append(':')
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderList(values: List<*>, renderSymbolsFully: Boolean) {
        if (values.isEmpty()) {
            append("[]")
            return
        }

        withIndentInSquareBrackets {
            printCollection(values, separator = "\n") { renderValue(it, renderSymbolsFully) }
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderSymbolTag(symbol: KtSymbol, renderSymbolsFully: Boolean) {
        fun renderId(id: Any?, symbol: KtSymbol) {
            if (id != null) {
                renderValue(id, renderSymbolsFully)
            } else {
                val outerName = (symbol as? KtPossiblyNamedSymbol)?.name ?: SpecialNames.NO_NAME_PROVIDED
                append("<local>/" + outerName.asString())
            }
        }

        if (renderSymbolsFully || symbol is KtPropertyGetterSymbol || symbol is KtPropertySetterSymbol || symbol is KtValueParameterSymbol || symbol is KtReceiverParameterSymbol) {
            renderSymbol(symbol)
            return
        }

        append(getSymbolApiClass(symbol).simpleName)
        append("(")
        when (symbol) {
            is KtClassLikeSymbol -> renderId(symbol.classIdIfNonLocal, symbol)
            is KtCallableSymbol -> renderId(symbol.callableIdIfNonLocal, symbol)
            is KtNamedSymbol -> renderValue(symbol.name, renderSymbolsFully = false)
            else -> error("Unsupported symbol ${symbol::class.java.name}")
        }
        append(")")
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderAnnotationValue(value: KtAnnotationValue) {
        append(KtAnnotationValueRenderer.render(value))
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderNamedConstantValue(value: KtNamedAnnotationValue) {
        append(value.name.render()).append(" = ")
        renderValue(value.expression, renderSymbolsFully = false)
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderType(type: KtType) {
        if (type.annotations.isNotEmpty()) {
            renderList(type.annotations, renderSymbolsFully = false)
            append(' ')
        }
        when (type) {
            is KtClassErrorType -> append("ERROR_TYPE")
            else -> append(type.asStringForDebugging())
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderAnnotationApplication(call: KtAnnotationApplication) {
        renderValue(call.classId, renderSymbolsFully = false)
        append('(')
        call.arguments.sortedBy { it.name }.forEachIndexed { index, value ->
            if (index > 0) {
                append(", ")
            }
            renderValue(value, renderSymbolsFully = false)
        }
        append(')')

        withIndent {
            appendLine().append("psi: ")
            renderValue(call.psi?.javaClass?.simpleName, renderSymbolsFully = false)
        }
    }

    private fun PrettyPrinter.renderDeprecationInfo(info: DeprecationInfo) {
        append("DeprecationInfo(")
        append("deprecationLevel=${info.deprecationLevel}, ")
        append("propagatesToOverrides=${info.propagatesToOverrides}, ")
        append("message=${info.message}")
        append(")")
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderValue(value: Any?, renderSymbolsFully: Boolean) {
        when (value) {
            // Symbol-related values
            is KtSymbol -> renderSymbolTag(value, renderSymbolsFully)
            is KtType -> renderType(value)
            is KtAnnotationValue -> renderAnnotationValue(value)
            is KtNamedAnnotationValue -> renderNamedConstantValue(value)
            is KtInitializerValue -> renderKtInitializerValue(value)
            is KtContextReceiver -> rendeContextReceiver(value)
            is KtAnnotationApplication -> renderAnnotationApplication(value)
            is KtAnnotationsList -> renderAnnotationsList(value)
            is KtModule -> renderKtModule(value)
            // Other custom values
            is Name -> append(value.asString())
            is FqName -> append(value.asString())
            is ClassId -> append(value.asString())
            is DeprecationInfo -> renderDeprecationInfo(value)
            is Visibility -> append(value::class.java.simpleName)
            // Unsigned integers
            is UByte -> append(value.toString())
            is UShort -> append(value.toString())
            is UInt -> append(value.toString())
            is ULong -> append(value.toString())
            // Java values
            is Enum<*> -> append(value.name)
            is List<*> -> renderList(value, renderSymbolsFully = false)
            else -> append(value.toString())
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.rendeContextReceiver(receiver: KtContextReceiver) {
        append("ContextReceiver(")
        receiver.label?.let { label ->
            renderValue(label, renderSymbolsFully = false)
            append("@")
        }
        renderType(receiver.type)
        append(")")
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderKtModule(ktModule: KtModule) {
        val ktModuleClass = ktModule::class.allSuperclasses.filter { it in ktModuleSubclasses }.first()
        append("${ktModuleClass.simpleName} \"${ktModule.moduleDescription}\"")
    }

    private fun KClass<*>.allSealedSubClasses(): List<KClass<*>> = buildList {
        add(this@allSealedSubClasses)
        sealedSubclasses.flatMapTo(this) { it.allSealedSubClasses() }
    }

    private val ktModuleSubclasses = KtModule::class.allSealedSubClasses().distinct().sortedWith { a, b ->
        when {
            a == b -> 0
            a.isSubclassOf(b) -> -1
            b.isSubclassOf(a) -> 1
            else -> 0
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderKtInitializerValue(value: KtInitializerValue) {
        when (value) {
            is KtConstantInitializerValue -> {
                append("KtConstantInitializerValue(")
                append(value.constant.renderAsKotlinConstant())
                append(")")
            }

            is KtNonConstantInitializerValue -> {
                append("KtNonConstantInitializerValue(")
                append(value.initializerPsi?.firstLineOfPsi() ?: "NO_PSI")
                append(")")
            }

            is KtConstantValueForAnnotation -> {
                append("KtConstantValueForAnnotation(")
                append(value.annotationValue.renderAsSourceCode())
                append(")")
            }
        }
    }

    context(KtAnalysisSession)
    private fun PrettyPrinter.renderAnnotationsList(value: KtAnnotationsList) {
        renderList(value.annotations, renderSymbolsFully = false)
    }

    private fun getSymbolApiClass(symbol: KtSymbol): KClass<*> {
        var current: Class<in KtSymbol> = symbol.javaClass

        while (true) {
            val className = current.name
            if (symbolImplementationPackageNames.none { className.startsWith("$it.") }) {
                return current.kotlin
            }
            current = current.superclass
        }
    }

    context(KtAnalysisSession)
    private fun PsiElement.firstLineOfPsi(): String {
        val text = text
        val lines = text.lines()
        return if (lines.count() <= 1) text
        else lines.first() + " ..."
    }

    private val ignoredPropertyNames = setOf("psi", "token")

    private val symbolImplementationPackageNames = listOf(
        "org.jetbrains.kotlin.analysis.api.fir",
        "org.jetbrains.kotlin.analysis.api.descriptors",
    )
}
