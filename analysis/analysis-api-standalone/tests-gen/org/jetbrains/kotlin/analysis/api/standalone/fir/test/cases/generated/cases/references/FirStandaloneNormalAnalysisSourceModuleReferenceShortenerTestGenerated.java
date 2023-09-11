/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.analysis.api.standalone.fir.test.cases.generated.cases.references;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.analysis.api.standalone.fir.test.AnalysisApiFirStandaloneModeTestConfiguratorFactory;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfiguratorFactoryData;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiTestConfigurator;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.TestModuleKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.FrontendKind;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisSessionMode;
import org.jetbrains.kotlin.analysis.test.framework.test.configurators.AnalysisApiMode;
import org.jetbrains.kotlin.analysis.api.impl.base.test.cases.references.AbstractReferenceShortenerTest;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.analysis.api.GenerateAnalysisApiTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("analysis/analysis-api/testData/components/referenceShortener/referenceShortener")
@TestDataPath("$PROJECT_ROOT")
public class FirStandaloneNormalAnalysisSourceModuleReferenceShortenerTestGenerated extends AbstractReferenceShortenerTest {
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
    public void testAllFilesPresentInReferenceShortener() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/referenceShortener/referenceShortener"), Pattern.compile("^(.+)\\.kt$"), null, true);
    }

    @Test
    @TestMetadata("annotaiton.kt")
    public void testAnnotaiton() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/annotaiton.kt");
    }

    @Test
    @TestMetadata("anonymousFunction_annotation.kt")
    public void testAnonymousFunction_annotation() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/anonymousFunction_annotation.kt");
    }

    @Test
    @TestMetadata("anonymousFunction_receiverType.kt")
    public void testAnonymousFunction_receiverType() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/anonymousFunction_receiverType.kt");
    }

    @Test
    @TestMetadata("anonymousFunction_returnType.kt")
    public void testAnonymousFunction_returnType() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/anonymousFunction_returnType.kt");
    }

    @Test
    @TestMetadata("callableFromDefaultImport.kt")
    public void testCallableFromDefaultImport() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/callableFromDefaultImport.kt");
    }

    @Test
    @TestMetadata("callableFromDefaultImport2.kt")
    public void testCallableFromDefaultImport2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/callableFromDefaultImport2.kt");
    }

    @Test
    @TestMetadata("callableFromExplicitImport.kt")
    public void testCallableFromExplicitImport() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/callableFromExplicitImport.kt");
    }

    @Test
    @TestMetadata("classScopes.kt")
    public void testClassScopes() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classScopes.kt");
    }

    @Test
    @TestMetadata("classScopes2.kt")
    public void testClassScopes2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classScopes2.kt");
    }

    @Test
    @TestMetadata("classScopes3.kt")
    public void testClassScopes3() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classScopes3.kt");
    }

    @Test
    @TestMetadata("classType.kt")
    public void testClassType() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classType.kt");
    }

    @Test
    @TestMetadata("classType2.kt")
    public void testClassType2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classType2.kt");
    }

    @Test
    @TestMetadata("classWithWrongNumberOfTypeArguments.kt")
    public void testClassWithWrongNumberOfTypeArguments() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classWithWrongNumberOfTypeArguments.kt");
    }

    @Test
    @TestMetadata("classesWithSameName.kt")
    public void testClassesWithSameName() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName.kt");
    }

    @Test
    @TestMetadata("classesWithSameName10.kt")
    public void testClassesWithSameName10() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName10.kt");
    }

    @Test
    @TestMetadata("classesWithSameName11.kt")
    public void testClassesWithSameName11() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName11.kt");
    }

    @Test
    @TestMetadata("classesWithSameName2.kt")
    public void testClassesWithSameName2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName2.kt");
    }

    @Test
    @TestMetadata("classesWithSameName3.kt")
    public void testClassesWithSameName3() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName3.kt");
    }

    @Test
    @TestMetadata("classesWithSameName4.kt")
    public void testClassesWithSameName4() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName4.kt");
    }

    @Test
    @TestMetadata("classesWithSameName5.kt")
    public void testClassesWithSameName5() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName5.kt");
    }

    @Test
    @TestMetadata("classesWithSameName6.kt")
    public void testClassesWithSameName6() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName6.kt");
    }

    @Test
    @TestMetadata("classesWithSameName7.kt")
    public void testClassesWithSameName7() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName7.kt");
    }

    @Test
    @TestMetadata("classesWithSameName8.kt")
    public void testClassesWithSameName8() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName8.kt");
    }

    @Test
    @TestMetadata("classesWithSameName9.kt")
    public void testClassesWithSameName9() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/classesWithSameName9.kt");
    }

    @Test
    @TestMetadata("companionClassLiteral.kt")
    public void testCompanionClassLiteral() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/companionClassLiteral.kt");
    }

    @Test
    @TestMetadata("companionClassLiteral2.kt")
    public void testCompanionClassLiteral2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/companionClassLiteral2.kt");
    }

    @Test
    @TestMetadata("companionClassLiteral3.kt")
    public void testCompanionClassLiteral3() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/companionClassLiteral3.kt");
    }

    @Test
    @TestMetadata("companionQualifier.kt")
    public void testCompanionQualifier() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/companionQualifier.kt");
    }

    @Test
    @TestMetadata("companionUsedOutOfClass.kt")
    public void testCompanionUsedOutOfClass() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/companionUsedOutOfClass.kt");
    }

    @Test
    @TestMetadata("constructorParameter.kt")
    public void testConstructorParameter() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/constructorParameter.kt");
    }

    @Test
    @TestMetadata("contextReceiver.kt")
    public void testContextReceiver() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/contextReceiver.kt");
    }

    @Test
    @TestMetadata("enumClassCompanionAlreadyImported.kt")
    public void testEnumClassCompanionAlreadyImported() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/enumClassCompanionAlreadyImported.kt");
    }

    @Test
    @TestMetadata("enumEntryInitUsesCompanion.kt")
    public void testEnumEntryInitUsesCompanion() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/enumEntryInitUsesCompanion.kt");
    }

    @Test
    @TestMetadata("enumEntryInitUsesCompanion2.kt")
    public void testEnumEntryInitUsesCompanion2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/enumEntryInitUsesCompanion2.kt");
    }

    @Test
    @TestMetadata("extensionFromObject.kt")
    public void testExtensionFromObject() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/extensionFromObject.kt");
    }

    @Test
    @TestMetadata("extensionFunction_objectReceiverWithOtherThisInScope.kt")
    public void testExtensionFunction_objectReceiverWithOtherThisInScope() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/extensionFunction_objectReceiverWithOtherThisInScope.kt");
    }

    @Test
    @TestMetadata("extensionProperty_objectReceiverWithOtherThisInScope.kt")
    public void testExtensionProperty_objectReceiverWithOtherThisInScope() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/extensionProperty_objectReceiverWithOtherThisInScope.kt");
    }

    @Test
    @TestMetadata("functionalType_parameterPosition.kt")
    public void testFunctionalType_parameterPosition() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/functionalType_parameterPosition.kt");
    }

    @Test
    @TestMetadata("kdoc.kt")
    public void testKdoc() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/kdoc.kt");
    }

    @Test
    @TestMetadata("kdocQualifierSelected.kt")
    public void testKdocQualifierSelected() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/kdocQualifierSelected.kt");
    }

    @Test
    @TestMetadata("kdocQualifierSelected_rootIdePrefix.kt")
    public void testKdocQualifierSelected_rootIdePrefix() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/kdocQualifierSelected_rootIdePrefix.kt");
    }

    @Test
    @TestMetadata("kdocUnresolved.kt")
    public void testKdocUnresolved() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/kdocUnresolved.kt");
    }

    @Test
    @TestMetadata("memberVsCompanionObjectMemberConflict.kt")
    public void testMemberVsCompanionObjectMemberConflict() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/memberVsCompanionObjectMemberConflict.kt");
    }

    @Test
    @TestMetadata("multipleImport.kt")
    public void testMultipleImport() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/multipleImport.kt");
    }

    @Test
    @TestMetadata("nestedClass.kt")
    public void testNestedClass() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClass.kt");
    }

    @Test
    @TestMetadata("nestedClass2.kt")
    public void testNestedClass2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClass2.kt");
    }

    @Test
    @TestMetadata("nestedClass3.kt")
    public void testNestedClass3() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClass3.kt");
    }

    @Test
    @TestMetadata("objectWithInvokeOperator.kt")
    public void testObjectWithInvokeOperator() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/objectWithInvokeOperator.kt");
    }

    @Test
    @TestMetadata("parameterTypeTopLevelTypeLoses.kt")
    public void testParameterTypeTopLevelTypeLoses() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/parameterTypeTopLevelTypeLoses.kt");
    }

    @Test
    @TestMetadata("qualifierOfUnresolvedReference.kt")
    public void testQualifierOfUnresolvedReference() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/qualifierOfUnresolvedReference.kt");
    }

    @Test
    @TestMetadata("receiver.kt")
    public void testReceiver() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/receiver.kt");
    }

    @Test
    @TestMetadata("receiver2.kt")
    public void testReceiver2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/receiver2.kt");
    }

    @Test
    @TestMetadata("receiver3.kt")
    public void testReceiver3() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/receiver3.kt");
    }

    @Test
    @TestMetadata("receiver4.kt")
    public void testReceiver4() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/receiver4.kt");
    }

    @Test
    @TestMetadata("referenceInNestedClass.kt")
    public void testReferenceInNestedClass() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/referenceInNestedClass.kt");
    }

    @Test
    @TestMetadata("sameNameDifferentParams.kt")
    public void testSameNameDifferentParams() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/sameNameDifferentParams.kt");
    }

    @Test
    @TestMetadata("sameNameDifferentReceiver.kt")
    public void testSameNameDifferentReceiver() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/sameNameDifferentReceiver.kt");
    }

    @Test
    @TestMetadata("sameTypeNamesWithinSameScopes.kt")
    public void testSameTypeNamesWithinSameScopes() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/sameTypeNamesWithinSameScopes.kt");
    }

    @Test
    @TestMetadata("selfPropertyChain.kt")
    public void testSelfPropertyChain() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/selfPropertyChain.kt");
    }

    @Test
    @TestMetadata("selfPropertyChain1.kt")
    public void testSelfPropertyChain1() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/selfPropertyChain1.kt");
    }

    @Test
    @TestMetadata("shortenAlreadyImportedClass.kt")
    public void testShortenAlreadyImportedClass() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/shortenAlreadyImportedClass.kt");
    }

    @Test
    @TestMetadata("shortenAlreadyImportedClass2.kt")
    public void testShortenAlreadyImportedClass2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/shortenAlreadyImportedClass2.kt");
    }

    @Test
    @TestMetadata("shortenAlreadyImportedFunction.kt")
    public void testShortenAlreadyImportedFunction() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/shortenAlreadyImportedFunction.kt");
    }

    @Test
    @TestMetadata("shortenAlreadyImportedFunction2.kt")
    public void testShortenAlreadyImportedFunction2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/shortenAlreadyImportedFunction2.kt");
    }

    @Test
    @TestMetadata("shortenAlreadyImportedFunction3.kt")
    public void testShortenAlreadyImportedFunction3() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/shortenAlreadyImportedFunction3.kt");
    }

    @Test
    @TestMetadata("shortenAlreadyImportedFunction4.kt")
    public void testShortenAlreadyImportedFunction4() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/shortenAlreadyImportedFunction4.kt");
    }

    @Test
    @TestMetadata("staticMethodFromBaseClassConflict.kt")
    public void testStaticMethodFromBaseClassConflict() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/staticMethodFromBaseClassConflict.kt");
    }

    @Test
    @TestMetadata("superClass.kt")
    public void testSuperClass() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/superClass.kt");
    }

    @Test
    @TestMetadata("superEntry.kt")
    public void testSuperEntry() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/superEntry.kt");
    }

    @Test
    @TestMetadata("typeArgument.kt")
    public void testTypeArgument() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/typeArgument.kt");
    }

    @Test
    @TestMetadata("typeParams.kt")
    public void testTypeParams() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/typeParams.kt");
    }

    @Test
    @TestMetadata("typeParams2.kt")
    public void testTypeParams2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/typeParams2.kt");
    }

    @Test
    @TestMetadata("vararg.kt")
    public void testVararg() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/vararg.kt");
    }

    @Test
    @TestMetadata("variable.kt")
    public void testVariable() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/variable.kt");
    }

    @Test
    @TestMetadata("variable2.kt")
    public void testVariable2() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/variable2.kt");
    }

    @Test
    @TestMetadata("variableAssignment.kt")
    public void testVariableAssignment() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/variableAssignment.kt");
    }

    @Test
    @TestMetadata("variableAssignment_plusAssignOperator.kt")
    public void testVariableAssignment_plusAssignOperator() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/variableAssignment_plusAssignOperator.kt");
    }

    @Test
    @TestMetadata("variableAssignment_plusOperator.kt")
    public void testVariableAssignment_plusOperator() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/variableAssignment_plusOperator.kt");
    }

    @Test
    @TestMetadata("variable_invokeOperator.kt")
    public void testVariable_invokeOperator() throws Exception {
        runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/variable_invokeOperator.kt");
    }

    @Nested
    @TestMetadata("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses")
    @TestDataPath("$PROJECT_ROOT")
    public class NestedClasses {
        @Test
        public void testAllFilesPresentInNestedClasses() throws Exception {
            KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses"), Pattern.compile("^(.+)\\.kt$"), null, true);
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes1.kt")
        public void testNestedClassFromSupertypes1() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes1.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes1_java.kt")
        public void testNestedClassFromSupertypes1_java() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes1_java.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes2.kt")
        public void testNestedClassFromSupertypes2() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes2.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes2_java.kt")
        public void testNestedClassFromSupertypes2_java() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes2_java.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes3.kt")
        public void testNestedClassFromSupertypes3() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes3.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes3_java.kt")
        public void testNestedClassFromSupertypes3_java() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes3_java.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes4.kt")
        public void testNestedClassFromSupertypes4() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes4.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes5.kt")
        public void testNestedClassFromSupertypes5() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes5.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes5_java.kt")
        public void testNestedClassFromSupertypes5_java() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes5_java.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes6.kt")
        public void testNestedClassFromSupertypes6() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes6.kt");
        }

        @Test
        @TestMetadata("nestedClassFromSupertypes6_java.kt")
        public void testNestedClassFromSupertypes6_java() throws Exception {
            runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/nestedClassFromSupertypes6_java.kt");
        }

        @Nested
        @TestMetadata("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions")
        @TestDataPath("$PROJECT_ROOT")
        public class ClassHeaderPositions {
            @Test
            public void testAllFilesPresentInClassHeaderPositions() throws Exception {
                KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions"), Pattern.compile("^(.+)\\.kt$"), null, true);
            }

            @Test
            @TestMetadata("annotationOnClass.kt")
            public void testAnnotationOnClass() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/annotationOnClass.kt");
            }

            @Test
            @TestMetadata("annotationOnClass_nested.kt")
            public void testAnnotationOnClass_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/annotationOnClass_nested.kt");
            }

            @Test
            @TestMetadata("annotationOnConstructor.kt")
            public void testAnnotationOnConstructor() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/annotationOnConstructor.kt");
            }

            @Test
            @TestMetadata("annotationOnConstructor_nested.kt")
            public void testAnnotationOnConstructor_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/annotationOnConstructor_nested.kt");
            }

            @Test
            @TestMetadata("annotationOnParameter.kt")
            public void testAnnotationOnParameter() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/annotationOnParameter.kt");
            }

            @Test
            @TestMetadata("annotationOnParameter_nested.kt")
            public void testAnnotationOnParameter_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/annotationOnParameter_nested.kt");
            }

            @Test
            @TestMetadata("constructorParameterVsTopLevelProperty_conflict.kt")
            public void testConstructorParameterVsTopLevelProperty_conflict() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/constructorParameterVsTopLevelProperty_conflict.kt");
            }

            @Test
            @TestMetadata("constructorParameterVsTopLevelProperty_noConflict.kt")
            public void testConstructorParameterVsTopLevelProperty_noConflict() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/constructorParameterVsTopLevelProperty_noConflict.kt");
            }

            @Test
            @TestMetadata("contextReceiver.kt")
            public void testContextReceiver() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/contextReceiver.kt");
            }

            @Test
            @TestMetadata("contextReceiver_nested.kt")
            public void testContextReceiver_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/contextReceiver_nested.kt");
            }

            @Test
            @TestMetadata("primaryConstructorParameter.kt")
            public void testPrimaryConstructorParameter() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/primaryConstructorParameter.kt");
            }

            @Test
            @TestMetadata("primaryConstructorParameter_nested.kt")
            public void testPrimaryConstructorParameter_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/primaryConstructorParameter_nested.kt");
            }

            @Test
            @TestMetadata("superType.kt")
            public void testSuperType() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superType.kt");
            }

            @Test
            @TestMetadata("superTypeConstructor.kt")
            public void testSuperTypeConstructor() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeConstructor.kt");
            }

            @Test
            @TestMetadata("superTypeConstructorArgument.kt")
            public void testSuperTypeConstructorArgument() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeConstructorArgument.kt");
            }

            @Test
            @TestMetadata("superTypeConstructorArgument_nested.kt")
            public void testSuperTypeConstructorArgument_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeConstructorArgument_nested.kt");
            }

            @Test
            @TestMetadata("superTypeConstructor_nested.kt")
            public void testSuperTypeConstructor_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeConstructor_nested.kt");
            }

            @Test
            @TestMetadata("superTypeDelegation.kt")
            public void testSuperTypeDelegation() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeDelegation.kt");
            }

            @Test
            @TestMetadata("superTypeDelegation_nested.kt")
            public void testSuperTypeDelegation_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeDelegation_nested.kt");
            }

            @Test
            @TestMetadata("superTypeTypeArguments.kt")
            public void testSuperTypeTypeArguments() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeTypeArguments.kt");
            }

            @Test
            @TestMetadata("superTypeTypeArguments_nested.kt")
            public void testSuperTypeTypeArguments_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superTypeTypeArguments_nested.kt");
            }

            @Test
            @TestMetadata("superType_nested.kt")
            public void testSuperType_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/superType_nested.kt");
            }

            @Test
            @TestMetadata("typeBound.kt")
            public void testTypeBound() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/typeBound.kt");
            }

            @Test
            @TestMetadata("typeBound_nested.kt")
            public void testTypeBound_nested() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/typeBound_nested.kt");
            }

            @Test
            @TestMetadata("typeBound_whereClause.kt")
            public void testTypeBound_whereClause() throws Exception {
                runTest("analysis/analysis-api/testData/components/referenceShortener/referenceShortener/nestedClasses/classHeaderPositions/typeBound_whereClause.kt");
            }
        }
    }
}
