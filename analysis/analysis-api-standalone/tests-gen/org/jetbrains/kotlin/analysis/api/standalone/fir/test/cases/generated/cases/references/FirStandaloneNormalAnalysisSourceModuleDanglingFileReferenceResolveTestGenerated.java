/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.generated.cases.references;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.analysis.api.standalone.fir.test.configurators.AnalysisApiFirStandaloneModeTestConfiguratorFactory;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfiguratorFactoryData;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfigurator;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.TestModuleKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.FrontendKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisSessionMode;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiMode;
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.references.AbstractDanglingFileReferenceResolveTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/danglingFileReferenceResolve")
@TestDataPath("$PROJECT_ROOT")
public class FirStandaloneNormalAnalysisSourceModuleDanglingFileReferenceResolveTestGenerated extends AbstractDanglingFileReferenceResolveTest {
    @NotNull
    @Override
    public AnalysisApiTestConfigurator getConfigurator() {
        return AnalysisApiFirStandaloneModeTestConfiguratorFactory.INSTANCE.createConfigurator(
            new AnalysisApiTestConfiguratorFactoryData(
                FrontendKind.Fir,
                TestModuleKind.Source,
                AnalysisSessionMode.Normal,
                AnalysisApiMode.Standalone
            )
        );
    }

    @Test
    public void testAllFilesPresentInDanglingFileReferenceResolve() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/danglingFileReferenceResolve"), Pattern.compile("^([^.]+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("classDifferentFile.kt")
    public void testClassDifferentFile() throws Exception {
        runTest("analysis/analysis-api/testData/danglingFileReferenceResolve/classDifferentFile.kt");
    }

    @Test
    @TestMetadata("classSameFile.kt")
    public void testClassSameFile() throws Exception {
        runTest("analysis/analysis-api/testData/danglingFileReferenceResolve/classSameFile.kt");
    }

    @Test
    @TestMetadata("functionDifferentFile.kt")
    public void testFunctionDifferentFile() throws Exception {
        runTest("analysis/analysis-api/testData/danglingFileReferenceResolve/functionDifferentFile.kt");
    }

    @Test
    @TestMetadata("functionSameFile.kt")
    public void testFunctionSameFile() throws Exception {
        runTest("analysis/analysis-api/testData/danglingFileReferenceResolve/functionSameFile.kt");
    }
}
