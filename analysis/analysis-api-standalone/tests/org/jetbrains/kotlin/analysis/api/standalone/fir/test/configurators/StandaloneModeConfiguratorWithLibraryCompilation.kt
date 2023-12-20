/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.configurators

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.analysis.api.impl.base.test.configurators.AnalysisApiLibraryBaseTestServiceRegistrar
import org.jetbrains.kotlin.analysis.api.standalone.base.project.structure.KtModuleProjectStructure
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestServiceRegistrar
import org.jetbrains.kotlin.test.builders.TestConfigurationBuilder
import org.jetbrains.kotlin.test.services.TestModuleStructure
import org.jetbrains.kotlin.test.services.TestServices

object StandaloneModeConfiguratorWithLibraryCompilation : StandaloneModeConfiguratorBase() {
    override fun configureTest(builder: TestConfigurationBuilder, disposable: Disposable) {
        StandaloneModeConfigurator.configureTest(builder, disposable)

        StandaloneModeLibraryBinaryTestConfigurator.configureLibraryCompilationSupport(builder)
    }

    override val serviceRegistrars: List<AnalysisApiTestServiceRegistrar>
        get() = StandaloneModeConfigurator.serviceRegistrars + AnalysisApiLibraryBaseTestServiceRegistrar

    override fun createModules(
        moduleStructure: TestModuleStructure,
        testServices: TestServices,
        project: Project
    ): KtModuleProjectStructure {
        return StandaloneModeConfigurator.createModules(moduleStructure, testServices, project)
    }
}
