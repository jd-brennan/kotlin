/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.fir.test.cases.generated.cases.components.scopeProvider;

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
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.components.scopeProvider.AbstractSubstitutionOverridesUnwrappingTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping")
@TestDataPath("$PROJECT_ROOT")
public class FirIdeNormalAnalysisSourceModuleSubstitutionOverridesUnwrappingTestGenerated extends AbstractSubstitutionOverridesUnwrappingTest {
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
    public void testAllFilesPresentInSubstitutionOverridesUnwrapping() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("ClassWithGenericBase1.kt")
    public void testClassWithGenericBase1() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/ClassWithGenericBase1.kt");
    }

    @Test
    @TestMetadata("ClassWithGenericBase2.kt")
    public void testClassWithGenericBase2() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/ClassWithGenericBase2.kt");
    }

    @Test
    @TestMetadata("ClassWithGenericBase3.kt")
    public void testClassWithGenericBase3() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/ClassWithGenericBase3.kt");
    }

    @Test
    @TestMetadata("ClassWithGenericBase4.kt")
    public void testClassWithGenericBase4() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/ClassWithGenericBase4.kt");
    }

    @Test
    @TestMetadata("GenericFromFunctionInLocalClass1.kt")
    public void testGenericFromFunctionInLocalClass1() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/GenericFromFunctionInLocalClass1.kt");
    }

    @Test
    @TestMetadata("GenericFromFunctionInLocalClass2.kt")
    public void testGenericFromFunctionInLocalClass2() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/GenericFromFunctionInLocalClass2.kt");
    }

    @Test
    @TestMetadata("GenericFromOuterClassInInnerClass1.kt")
    public void testGenericFromOuterClassInInnerClass1() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/GenericFromOuterClassInInnerClass1.kt");
    }

    @Test
    @TestMetadata("GenericFromOuterClassInInnerClass2.kt")
    public void testGenericFromOuterClassInInnerClass2() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/GenericFromOuterClassInInnerClass2.kt");
    }

    @Test
    @TestMetadata("GenericFromOuterClassInInnerClassInInheritor1.kt")
    public void testGenericFromOuterClassInInnerClassInInheritor1() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/GenericFromOuterClassInInnerClassInInheritor1.kt");
    }

    @Test
    @TestMetadata("GenericFromOuterClassInInnerClassInInheritor2.kt")
    public void testGenericFromOuterClassInInnerClassInInheritor2() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/GenericFromOuterClassInInnerClassInInheritor2.kt");
    }

    @Test
    @TestMetadata("GenericFromOuterClassInInnerClassInInheritor3.kt")
    public void testGenericFromOuterClassInInnerClassInInheritor3() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/GenericFromOuterClassInInnerClassInInheritor3.kt");
    }

    @Test
    @TestMetadata("Implement_java_util_Collection.kt")
    public void testImplement_java_util_Collection() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/Implement_java_util_Collection.kt");
    }

    @Test
    @TestMetadata("MemberFunctionWithOuterTypeParameterBound.kt")
    public void testMemberFunctionWithOuterTypeParameterBound() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/MemberFunctionWithOuterTypeParameterBound.kt");
    }

    @Test
    @TestMetadata("MemberPropertyWithOuterTypeParameterBound.kt")
    public void testMemberPropertyWithOuterTypeParameterBound() throws Exception {
        runTest("analysis/analysis-api/testData/components/scopeProvider/substitutionOverridesUnwrapping/MemberPropertyWithOuterTypeParameterBound.kt");
    }
}
