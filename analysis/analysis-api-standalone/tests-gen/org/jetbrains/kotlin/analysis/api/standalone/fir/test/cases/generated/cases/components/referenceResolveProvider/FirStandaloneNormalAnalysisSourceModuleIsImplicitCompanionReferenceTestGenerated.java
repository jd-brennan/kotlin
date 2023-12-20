/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.generated.cases.components.referenceResolveProvider;

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
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.components.referenceResolveProvider.AbstractIsImplicitCompanionReferenceTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion")
@TestDataPath("$PROJECT_ROOT")
public class FirStandaloneNormalAnalysisSourceModuleIsImplicitCompanionReferenceTestGenerated extends AbstractIsImplicitCompanionReferenceTest {
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
    public void testAllFilesPresentInIsImplicitReferenceToCompanion() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("classAccessWithExplicitReferenceToCompanion.kt")
    public void testClassAccessWithExplicitReferenceToCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/classAccessWithExplicitReferenceToCompanion.kt");
    }

    @Test
    @TestMetadata("classAccessWithExplicitReferenceToNamedCompanion.kt")
    public void testClassAccessWithExplicitReferenceToNamedCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/classAccessWithExplicitReferenceToNamedCompanion.kt");
    }

    @Test
    @TestMetadata("constructorCall.kt")
    public void testConstructorCall() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/constructorCall.kt");
    }

    @Test
    @TestMetadata("constructorCallWithInvokeInCompanion.kt")
    public void testConstructorCallWithInvokeInCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/constructorCallWithInvokeInCompanion.kt");
    }

    @Test
    @TestMetadata("explicitReferenceToCompanion.kt")
    public void testExplicitReferenceToCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/explicitReferenceToCompanion.kt");
    }

    @Test
    @TestMetadata("explicitReferenceToNamedCompanion.kt")
    public void testExplicitReferenceToNamedCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/explicitReferenceToNamedCompanion.kt");
    }

    @Test
    @TestMetadata("impicitReferenceToCompanion.kt")
    public void testImpicitReferenceToCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/impicitReferenceToCompanion.kt");
    }

    @Test
    @TestMetadata("qualifierReferenceToClassWithCompanion.kt")
    public void testQualifierReferenceToClassWithCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceResolveProvider/isImplicitReferenceToCompanion/qualifierReferenceToClassWithCompanion.kt");
    }
}
