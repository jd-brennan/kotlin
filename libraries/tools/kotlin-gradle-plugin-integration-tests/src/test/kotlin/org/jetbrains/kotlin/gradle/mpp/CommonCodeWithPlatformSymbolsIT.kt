/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.mpp

import org.gradle.api.logging.LogLevel
import org.gradle.util.GradleVersion
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute
import org.jetbrains.kotlin.gradle.testbase.*
import org.jetbrains.kotlin.gradle.util.replaceWithVersion
import org.jetbrains.kotlin.test.TestMetadata
import org.junit.jupiter.api.DisplayName

@MppGradlePluginTests
@DisplayName("Tests for IC with compatible overloads in common and platform sourceSets")
class CommonCodeWithPlatformSymbolsIT : KGPBaseTest() {
    override val defaultBuildOptions: BuildOptions
        get() = super.defaultBuildOptions.copy(
            logLevel = LogLevel.DEBUG,
            languageVersion = "2.0",
            enableUnsafeIncrementalCompilationForMultiplatform = false
        )

    //TODO tests
    /**
     * call changes A,B,C
     *
     * base scenario: unsafe on -> build fails in ABC
     * unsafe off -> build successful, starts incremental, then rebuilds - assert properly
     *
     * test js version
     */

    @GradleTest
    @DisplayName("Use member function of an expect class that is overloaded in the actual class")
    @TestMetadata("kt-62686-mpp-jvm-expect-dep")
    fun testIncrementalBuildWithOverloadedMemberFunction(gradleVersion: GradleVersion) {
        project(
            "kt-62686-mpp-jvm-expect-dep", gradleVersion
        ) {
            val taskToExecute = ":compileKotlinJvm"
            build(taskToExecute) {
                assertTasksExecuted(taskToExecute)
            }

            projectPath.resolve("src/commonMain/kotlin/commonTest.kt").replaceWithVersion("useMemberFunctionFromExpectClass")

            build(taskToExecute) {
                printBuildOutput()
                assertTasksExecuted(taskToExecute)
                assertNonIncrementalCompilation(BuildAttribute.UNSAFE_INCREMENTAL_CHANGE_KT_62686)
            }
        }
    }

    @GradleTest
    @DisplayName("Use top level function that is overloaded in platform sourceSet")
    @TestMetadata("kt-62686-mpp-jvm-expect-dep")
    fun testIncrementalBuildWithOverloadedTopLevelFunction(gradleVersion: GradleVersion) {
        project(
            "kt-62686-mpp-jvm-expect-dep", gradleVersion
        ) {
            val taskToExecute = ":compileKotlinJvm"
            build(taskToExecute) {
                assertTasksExecuted(taskToExecute)
            }

            projectPath.resolve("src/commonMain/kotlin/commonTest.kt").replaceWithVersion("useTopLevelFunction")

            build(taskToExecute) {
                assertTasksExecuted(taskToExecute)
                assertNonIncrementalCompilation(BuildAttribute.UNSAFE_INCREMENTAL_CHANGE_KT_62686)
            }
        }
    }
}