/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.backend.jvm.codegen

import org.jetbrains.kotlin.backend.common.lower.ANNOTATION_IMPLEMENTATION
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities
import org.jetbrains.kotlin.ir.IrDiagnosticReporter
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.descriptors.toIrBasedDescriptor
import org.jetbrains.kotlin.ir.linkage.SignatureClashDetector
import org.jetbrains.kotlin.ir.util.isFakeOverride
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ConflictingJvmDeclarationsData
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors
import org.jetbrains.kotlin.resolve.jvm.diagnostics.MemberKind
import org.jetbrains.kotlin.resolve.jvm.diagnostics.RawSignature

internal class JvmMethodSignatureClashDetector(
    private val classCodegen: ClassCodegen
) : SignatureClashDetector<RawSignature, IrFunction>() {

    override val predefinedSignatures: List<RawSignature>
        get() = PREDEFINED_SIGNATURES

    override fun reportConflictingDeclarations(
        signature: RawSignature,
        declarations: Collection<IrFunction>,
        diagnosticReporter: IrDiagnosticReporter
    ) {
        // In IFoo$DefaultImpls we should report errors only if there are private methods among conflicting ones
        // (otherwise such errors would be reported twice: once for IFoo and once for IFoo$DefaultImpls).
        if (classCodegen.irClass.origin != JvmLoweredDeclarationOrigin.DEFAULT_IMPLS ||
            declarations.any { DescriptorVisibilities.isPrivate(it.visibility) }
        ) {
            reportSignatureClashTo(
                diagnosticReporter,
                JvmBackendErrors.CONFLICTING_JVM_DECLARATIONS,
                declarations,
                getConflictingJvmDeclarationsData(signature, declarations),
                reportOnIfSynthetic = { classCodegen.irClass },
            )
        }
    }

    override fun reportConflictingInheritedDeclarations(
        signature: RawSignature,
        declarations: Collection<IrFunction>,
        diagnosticReporter: IrDiagnosticReporter
    ) {
        if (classCodegen.irClass.origin != JvmLoweredDeclarationOrigin.DEFAULT_IMPLS) {
            reportSignatureClashTo(
                diagnosticReporter,
                JvmBackendErrors.CONFLICTING_INHERITED_JVM_DECLARATIONS,
                listOf(classCodegen.irClass),
                getConflictingJvmDeclarationsData(signature, declarations),
                reportOnIfSynthetic = { it },
            )
        }
    }

    override fun reportAccidentalOverride(
        signature: RawSignature,
        declarations: Collection<IrFunction>,
        diagnosticReporter: IrDiagnosticReporter
    ) {
        if (classCodegen.irClass.origin != JvmLoweredDeclarationOrigin.DEFAULT_IMPLS) {
            reportSignatureClashTo(
                diagnosticReporter,
                JvmBackendErrors.ACCIDENTAL_OVERRIDE,
                declarations.filter { !it.isFakeOverride && !it.isSpecialOverride() },
                getConflictingJvmDeclarationsData(signature, declarations),
                reportOnIfSynthetic = { classCodegen.irClass },
            )
        }
    }

    override fun reportPredefinedMethodSignatureConflict(
        signature: RawSignature,
        declarations: Collection<IrFunction>,
        diagnosticReporter: IrDiagnosticReporter
    ) {
        val conflictingJvmDeclarationsData = ConflictingJvmDeclarationsData(
            classCodegen.type.internalName, null, signature, null, declarations.map(IrFunction::toIrBasedDescriptor),
        )
        reportSignatureClashTo(
            diagnosticReporter,
            JvmBackendErrors.ACCIDENTAL_OVERRIDE,
            declarations,
            conflictingJvmDeclarationsData,
            reportOnIfSynthetic = { classCodegen.irClass }
        )
    }

    /**
     * Records the fake override method, so it could later participate in signature clash detection.
     */
    fun trackFakeOverrideMethod(irFunction: IrFunction) {
        if (irFunction.dispatchReceiverParameter != null) {
            for (overriddenFunction in getOverriddenFunctions(irFunction as IrSimpleFunction)) {
                if (!overriddenFunction.isFakeOverride) trackDeclaration(irFunction, mapRawSignature(overriddenFunction))
            }
        } else {
            trackDeclaration(irFunction, mapRawSignature(irFunction))
        }
    }

    private fun mapRawSignature(irFunction: IrFunction): RawSignature {
        val jvmSignature = classCodegen.methodSignatureMapper.mapFakeOverrideSignatureSkipGeneric(irFunction)
        return RawSignature(jvmSignature.asmMethod.name, jvmSignature.asmMethod.descriptor, MemberKind.METHOD)
    }

    private fun getOverriddenFunctions(irFunction: IrSimpleFunction): Set<IrFunction> {
        val result = LinkedHashSet<IrFunction>()
        collectOverridesOf(irFunction, result)
        return result
    }

    private fun collectOverridesOf(irFunction: IrSimpleFunction, result: MutableSet<IrFunction>) {
        for (overriddenSymbol in irFunction.overriddenSymbols) {
            collectOverridesTree(overriddenSymbol.owner, result)
        }
    }

    private fun collectOverridesTree(irFunction: IrSimpleFunction, visited: MutableSet<IrFunction>) {
        if (!visited.add(irFunction)) return
        collectOverridesOf(irFunction, visited)
    }

    override fun IrFunction.isSpecialOverride(): Boolean =
        origin in SPECIAL_BRIDGES_AND_OVERRIDES

    private fun getConflictingJvmDeclarationsData(
        rawSignature: RawSignature,
        methods: Collection<IrDeclaration>
    ): ConflictingJvmDeclarationsData =
        ConflictingJvmDeclarationsData(
            classCodegen.type.internalName, null, rawSignature, null, methods.map(IrDeclaration::toIrBasedDescriptor),
        )

    companion object {
        val SPECIAL_BRIDGES_AND_OVERRIDES = setOf(
            IrDeclarationOrigin.BRIDGE,
            IrDeclarationOrigin.BRIDGE_SPECIAL,
            IrDeclarationOrigin.IR_BUILTINS_STUB,
            JvmLoweredDeclarationOrigin.TO_ARRAY,
            JvmLoweredDeclarationOrigin.SUPER_INTERFACE_METHOD_BRIDGE,
            ANNOTATION_IMPLEMENTATION
        )

        val PREDEFINED_SIGNATURES = listOf(
            RawSignature("getClass", "()Ljava/lang/Class;", MemberKind.METHOD),
            RawSignature("notify", "()V", MemberKind.METHOD),
            RawSignature("notifyAll", "()V", MemberKind.METHOD),
            RawSignature("wait", "()V", MemberKind.METHOD),
            RawSignature("wait", "(J)V", MemberKind.METHOD),
            RawSignature("wait", "(JI)V", MemberKind.METHOD)
        )
    }
}
