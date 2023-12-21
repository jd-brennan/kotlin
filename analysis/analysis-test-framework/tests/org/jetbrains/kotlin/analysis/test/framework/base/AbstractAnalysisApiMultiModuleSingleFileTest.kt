/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.test.framework.base

import org.jetbrains.kotlin.analysis.test.framework.project.structure.ktModuleProvider
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.test.model.TestModule
import org.jetbrains.kotlin.test.services.TestModuleStructure
import org.jetbrains.kotlin.test.services.TestServices

/**
 * [AbstractAnalysisApiMultiModuleSingleFileTest] supports single-file tests with a single or multiple modules. The last module is
 * considered the "main" module. It must contain a single file with which [doTestByFileStructure] will be invoked.
 */
abstract class AbstractAnalysisApiMultiModuleSingleFileTest : AbstractAnalysisApiBasedTest() {
    final override fun doTestByModuleStructure(moduleStructure: TestModuleStructure, testServices: TestServices) {
        val mainModule = moduleStructure.modules.last()
        val mainKtFile = testServices.ktModuleProvider.getModuleFiles(mainModule).firstNotNullOf { it as? KtFile }
        doTestByFileStructure(mainKtFile, mainModule, testServices)
    }

    abstract fun doTestByFileStructure(ktFile: KtFile, mainModule: TestModule, testServices: TestServices)
}
