/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.generated.cases.annotations;

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
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.annotations.AbstractAnalysisApiAnnotationsOnDeclarationsTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/annotations/annotationsOnDeclaration")
@TestDataPath("$PROJECT_ROOT")
public class FirStandaloneNormalAnalysisSourceModuleAnalysisApiAnnotationsOnDeclarationsTestGenerated extends AbstractAnalysisApiAnnotationsOnDeclarationsTest {
    @NotNull
    @Override
    public AnalysisApiTestConfigurator getConfigurator() {
        return AnalysisApiFirStandaloneModeTestConfiguratorFactory.INSTANCE.createConfigurator(
            new AnalysisApiTestConfiguratorFactoryData(
                FrontendKind.Fir,
                TestModuleKind.Source,
                AnalysisSessionMode.Normal,
                AnalysisApiMode.Standalone,
                false
            )
        );
    }

    @Test
    public void testAllFilesPresentInAnnotationsOnDeclaration() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/annotations/annotationsOnDeclaration"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("deprecated.kt")
    public void testDeprecated() throws Exception {
        runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/deprecated.kt");
    }

    @Nested
    @TestMetadata("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct")
    @TestDataPath("$PROJECT_ROOT")
    public class Direct {
        @Test
        @TestMetadata("aliasedThrowsOnFunction.kt")
        public void testAliasedThrowsOnFunction() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/aliasedThrowsOnFunction.kt");
        }

        @Test
        public void testAllFilesPresentInDirect() throws Exception {
            KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct"), Pattern.compile("^(.+)\\.kt$"), null, true);
        }

        @Test
        @TestMetadata("emptyJavaSpreadParameter.kt")
        public void testEmptyJavaSpreadParameter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/emptyJavaSpreadParameter.kt");
        }

        @Test
        @TestMetadata("onClass.kt")
        public void testOnClass() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/onClass.kt");
        }

        @Test
        @TestMetadata("onFunction.kt")
        public void testOnFunction() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/onFunction.kt");
        }

        @Test
        @TestMetadata("onFunction_unresolvedClassReference.kt")
        public void testOnFunction_unresolvedClassReference() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/onFunction_unresolvedClassReference.kt");
        }

        @Test
        @TestMetadata("onProperty.kt")
        public void testOnProperty() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/onProperty.kt");
        }

        @Test
        @TestMetadata("onProperty_javaAnnotation_targets.kt")
        public void testOnProperty_javaAnnotation_targets() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/onProperty_javaAnnotation_targets.kt");
        }

        @Test
        @TestMetadata("onTypeAlias.kt")
        public void testOnTypeAlias() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/onTypeAlias.kt");
        }

        @Test
        @TestMetadata("unsignedParameter.kt")
        public void testUnsignedParameter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/unsignedParameter.kt");
        }

        @Test
        @TestMetadata("varargComplexParameter.kt")
        public void testVarargComplexParameter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/varargComplexParameter.kt");
        }

        @Test
        @TestMetadata("varargNamedParameter.kt")
        public void testVarargNamedParameter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/varargNamedParameter.kt");
        }

        @Test
        @TestMetadata("varargParameter.kt")
        public void testVarargParameter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/varargParameter.kt");
        }

        @Test
        @TestMetadata("varargSpreadParameter.kt")
        public void testVarargSpreadParameter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/direct/varargSpreadParameter.kt");
        }
    }

    @Nested
    @TestMetadata("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/useSite")
    @TestDataPath("$PROJECT_ROOT")
    public class UseSite {
        @Test
        public void testAllFilesPresentInUseSite() throws Exception {
            KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/useSite"), Pattern.compile("^(.+)\\.kt$"), null, true);
        }

        @Test
        @TestMetadata("onGetter.kt")
        public void testOnGetter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/useSite/onGetter.kt");
        }

        @Test
        @TestMetadata("onParam.kt")
        public void testOnParam() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/useSite/onParam.kt");
        }

        @Test
        @TestMetadata("onProperty.kt")
        public void testOnProperty() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/useSite/onProperty.kt");
        }

        @Test
        @TestMetadata("onSetter.kt")
        public void testOnSetter() throws Exception {
            runTest("analysis/analysis-api/testData/annotations/annotationsOnDeclaration/useSite/onSetter.kt");
        }
    }
}
