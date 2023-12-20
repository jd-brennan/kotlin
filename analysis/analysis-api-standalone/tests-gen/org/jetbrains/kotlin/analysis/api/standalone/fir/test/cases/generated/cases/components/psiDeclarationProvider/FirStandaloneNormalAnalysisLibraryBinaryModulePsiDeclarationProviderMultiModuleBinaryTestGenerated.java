/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.generated.cases.components.psiDeclarationProvider;

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
import org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.components.psiDeclarationProvider.AbstractPsiDeclarationProviderMultiModuleBinaryTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/standalone/multiModuleBinary")
@TestDataPath("$PROJECT_ROOT")
public class FirStandaloneNormalAnalysisLibraryBinaryModulePsiDeclarationProviderMultiModuleBinaryTestGenerated extends AbstractPsiDeclarationProviderMultiModuleBinaryTest {
    @NotNull
    @Override
    public AnalysisApiTestConfigurator getConfigurator() {
        return AnalysisApiFirStandaloneModeTestConfiguratorFactory.INSTANCE.createConfigurator(
            new AnalysisApiTestConfiguratorFactoryData(
                FrontendKind.Fir,
                TestModuleKind.LibraryBinary,
                AnalysisSessionMode.Normal,
                AnalysisApiMode.Standalone,
                false
            )
        );
    }

    @Test
    public void testAllFilesPresentInMultiModuleBinary() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/standalone/multiModuleBinary"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("multifileFacade.kt")
    public void testMultifileFacade() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/multifileFacade.kt");
    }

    @Test
    @TestMetadata("propertiesInCompanionObject.kt")
    public void testPropertiesInCompanionObject() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInCompanionObject.kt");
    }

    @Test
    @TestMetadata("propertiesInCompanionObject_JvmField.kt")
    public void testPropertiesInCompanionObject_JvmField() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInCompanionObject_JvmField.kt");
    }

    @Test
    @TestMetadata("propertiesInCompanionObject_JvmStatic.kt")
    public void testPropertiesInCompanionObject_JvmStatic() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInCompanionObject_JvmStatic.kt");
    }

    @Test
    @TestMetadata("propertiesInInnerClass.kt")
    public void testPropertiesInInnerClass() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInInnerClass.kt");
    }

    @Test
    @TestMetadata("propertiesInNamedCompanionObject.kt")
    public void testPropertiesInNamedCompanionObject() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInNamedCompanionObject.kt");
    }

    @Test
    @TestMetadata("propertiesInNamedCompanionObject_JvmField.kt")
    public void testPropertiesInNamedCompanionObject_JvmField() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInNamedCompanionObject_JvmField.kt");
    }

    @Test
    @TestMetadata("propertiesInNamedCompanionObject_JvmStatic.kt")
    public void testPropertiesInNamedCompanionObject_JvmStatic() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInNamedCompanionObject_JvmStatic.kt");
    }

    @Test
    @TestMetadata("propertiesInNestedObject.kt")
    public void testPropertiesInNestedObject() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInNestedObject.kt");
    }

    @Test
    @TestMetadata("propertiesInObject.kt")
    public void testPropertiesInObject() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInObject.kt");
    }

    @Test
    @TestMetadata("propertiesInOuterClass.kt")
    public void testPropertiesInOuterClass() throws Exception {
        runTest("analysis/analysis-api/testData/standalone/multiModuleBinary/propertiesInOuterClass.kt");
    }
}
