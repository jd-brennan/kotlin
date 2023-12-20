/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.fir.test.cases.generated.cases.annotations;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.analysis.api.fir.test.configurators.AnalysisApiFirTestConfiguratorFactory;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfiguratorFactoryData;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfigurator;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.TestModuleKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.FrontendKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisSessionMode;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiMode;
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.annotations.AbstractAnalysisApiSpecificAnnotationOnDeclarationTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/annotations/specificAnnotations")
@TestDataPath("$PROJECT_ROOT")
public class FirIdeNormalAnalysisSourceModuleAnalysisApiSpecificAnnotationOnDeclarationTestGenerated extends AbstractAnalysisApiSpecificAnnotationOnDeclarationTest {
    @NotNull
    @Override
    public AnalysisApiTestConfigurator getConfigurator() {
        return AnalysisApiFirTestConfiguratorFactory.INSTANCE.createConfigurator(
            new AnalysisApiTestConfiguratorFactoryData(
                FrontendKind.Fir,
                TestModuleKind.Source,
                AnalysisSessionMode.Normal,
                AnalysisApiMode.Ide,
                false
            )
        );
    }

    @Test
    public void testAllFilesPresentInSpecificAnnotations() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/annotations/specificAnnotations"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("javaTargetAnnotationWithEmptyArguments.kt")
    public void testJavaTargetAnnotationWithEmptyArguments() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/javaTargetAnnotationWithEmptyArguments.kt");
    }

    @Test
    @TestMetadata("javaTargetAnnotationWithOneArgument.kt")
    public void testJavaTargetAnnotationWithOneArgument() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/javaTargetAnnotationWithOneArgument.kt");
    }

    @Test
    @TestMetadata("javaTargetAnnotationWithOneArgumentAsImport.kt")
    public void testJavaTargetAnnotationWithOneArgumentAsImport() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/javaTargetAnnotationWithOneArgumentAsImport.kt");
    }

    @Test
    @TestMetadata("javaTargetAnnotationWithSeveralArguments.kt")
    public void testJavaTargetAnnotationWithSeveralArguments() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/javaTargetAnnotationWithSeveralArguments.kt");
    }

    @Test
    @TestMetadata("javaTargetAnnotationWithoutArguments.kt")
    public void testJavaTargetAnnotationWithoutArguments() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/javaTargetAnnotationWithoutArguments.kt");
    }

    @Test
    @TestMetadata("targetAnnotationWithEmptyArguments.kt")
    public void testTargetAnnotationWithEmptyArguments() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/targetAnnotationWithEmptyArguments.kt");
    }

    @Test
    @TestMetadata("targetAnnotationWithOneArgument.kt")
    public void testTargetAnnotationWithOneArgument() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/targetAnnotationWithOneArgument.kt");
    }

    @Test
    @TestMetadata("targetAnnotationWithOneArgumentAsImport.kt")
    public void testTargetAnnotationWithOneArgumentAsImport() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/targetAnnotationWithOneArgumentAsImport.kt");
    }

    @Test
    @TestMetadata("targetAnnotationWithSeveralArguments.kt")
    public void testTargetAnnotationWithSeveralArguments() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/targetAnnotationWithSeveralArguments.kt");
    }

    @Test
    @TestMetadata("targetAnnotationWithoutArguments.kt")
    public void testTargetAnnotationWithoutArguments() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/specificAnnotations/targetAnnotationWithoutArguments.kt");
    }
}
