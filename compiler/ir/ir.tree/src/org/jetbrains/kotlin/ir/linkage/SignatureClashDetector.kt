/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.linkage

import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactory1
import org.jetbrains.kotlin.ir.IrDiagnosticReporter
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.IrDeclaration
import org.jetbrains.kotlin.ir.util.file
import org.jetbrains.kotlin.ir.util.isFakeOverride
import org.jetbrains.kotlin.ir.util.sourceElement
import org.jetbrains.kotlin.utils.SmartSet

/**
 * Collects the information about declarations and their signatures and reports cases when multiple declarations have the same signature.
 */
abstract class SignatureClashDetector<Signature : Any, Declaration : IrDeclaration> {

    private val declarationsBySignature: HashMap<Signature, MutableSet<Declaration>> = LinkedHashMap()

    /**
     * Methods for which this function returns `true` are handled the same as fake overrides.
     */
    protected open fun Declaration.isSpecialOverride(): Boolean = false

    /**
     * Reports the case when only inherited methods (i.e., fake overrides) have conflicting signatures.
     */
    protected open fun reportConflictingInheritedDeclarations(
        signature: Signature,
        declarations: Collection<Declaration>,
        diagnosticReporter: IrDiagnosticReporter,
    ) {
    }

    /**
     * Reports the case when only "real" (i.e., non-fake-override) methods have conflicting signatures.
     */
    protected abstract fun reportConflictingDeclarations(
        signature: Signature,
        declarations: Collection<Declaration>,
        diagnosticReporter: IrDiagnosticReporter,
    )

    /**
     * Reports the case when there is at least one "real" (i.e., non-fake-override) method and at least one fake override method,
     * all of which have the same signature, resulting in accidental overriding.
     */
    protected open fun reportAccidentalOverride(
        signature: Signature,
        declarations: Collection<Declaration>,
        diagnosticReporter: IrDiagnosticReporter,
    ) {
    }

    /**
     * Reports the case when at least one method has the signature listed in [predefinedSignatures].
     */
    protected open fun reportPredefinedMethodSignatureConflict(
        signature: Signature,
        declarations: Collection<Declaration>,
        diagnosticReporter: IrDiagnosticReporter,
    ) {
    }

    /**
     * The list of signatures that no user-defined method can have.
     */
    protected open val predefinedSignatures: List<Signature>
        get() = emptyList()

    /**
     * Records the declaration, so it could later participate in signature clash detection.
     */
    fun trackDeclaration(declaration: Declaration, rawSignature: Signature) {
        declarationsBySignature.computeIfAbsent(rawSignature) { SmartSet.create() }.add(declaration)
    }

    /**
     * Reports all detected signature clashes.
     */
    fun reportErrorsTo(diagnosticReporter: IrDiagnosticReporter) {
        reportSignatureConflicts(diagnosticReporter)
        reportPredefinedSignatureConflicts(diagnosticReporter)
    }

    protected inline fun <Data : Any, ConflictingDeclaration : IrDeclaration> reportSignatureClashTo(
        diagnosticReporter: IrDiagnosticReporter,
        diagnosticFactory: KtDiagnosticFactory1<Data>,
        declarations: Collection<ConflictingDeclaration>,
        data: Data,
        reportOnIfSynthetic: (ConflictingDeclaration) -> IrElement,
    ) {
        declarations.mapTo(LinkedHashSet()) { declaration ->
            val reportOn = declaration.takeUnless { it.startOffset < 0 } ?: reportOnIfSynthetic(declaration)
            diagnosticReporter.at(reportOn.sourceElement(), reportOn, declaration.file)
        }.forEach {
            it.report(diagnosticFactory, data)
        }
    }

    private fun reportSignatureConflicts(diagnosticReporter: IrDiagnosticReporter) {
        for ((signature, methods) in declarationsBySignature) {
            if (methods.size <= 1) continue

            val fakeOverridesCount = methods.count { it.isFakeOverride }
            val specialOverridesCount = methods.count { it.isSpecialOverride() }
            val realMethodsCount = methods.size - fakeOverridesCount - specialOverridesCount

            when {
                realMethodsCount == 0 && (fakeOverridesCount > 1 || specialOverridesCount > 1) ->
                    reportConflictingInheritedDeclarations(signature, methods, diagnosticReporter)

                fakeOverridesCount == 0 && specialOverridesCount == 0 ->
                    reportConflictingDeclarations(signature, methods, diagnosticReporter)

                else -> reportAccidentalOverride(signature, methods, diagnosticReporter)
            }
        }
    }

    private fun reportPredefinedSignatureConflicts(diagnosticReporter: IrDiagnosticReporter) {
        for (predefinedSignature in predefinedSignatures) {
            val knownMethods = declarationsBySignature[predefinedSignature] ?: continue
            val methods = knownMethods.filter { !it.isFakeOverride && !it.isSpecialOverride() }
            if (methods.isEmpty()) continue
            reportPredefinedMethodSignatureConflict(predefinedSignature, methods, diagnosticReporter)
        }
    }
}