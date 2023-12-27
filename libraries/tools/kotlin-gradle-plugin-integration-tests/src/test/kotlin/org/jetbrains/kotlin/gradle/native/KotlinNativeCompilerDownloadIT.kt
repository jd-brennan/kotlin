/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.gradle.native

import org.gradle.util.GradleVersion
import org.jetbrains.kotlin.gradle.testbase.*
import org.jetbrains.kotlin.gradle.testbase.TestVersions.Kotlin.STABLE_RELEASE
import org.jetbrains.kotlin.gradle.util.capitalize
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.test.TestMetadata
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.appendText
import kotlin.test.assertContains


@DisplayName("This test class contains different scenarios with downloading Kotlin Native Compiler during build.")
@NativeGradlePluginTests
class KotlinNativeCompilerDownloadIT : KGPBaseTest() {

    private val currentPlatform = HostManager.platformName()

    private val nativeHostTargetName = MPPNativeTargets.current

    private val UNPUCK_KONAN_FINISHED_LOG =
        "Moving Kotlin/Native compiler from tmp directory"

    private val STABLE_VERSION_DIR_NAME = "kotlin-native-prebuilt-$currentPlatform-$STABLE_RELEASE"

    private val DOWNLOAD_KONAN_FINISHED_LOG =
        "Download $STABLE_VERSION_DIR_NAME.${if (HostManager.hostIsMingw) "zip" else "tar.gz"} finished,"

    override val defaultBuildOptions: BuildOptions
        get() = super.defaultBuildOptions.copy(
            nativeOptions = super.defaultBuildOptions.nativeOptions.copy(
                version = STABLE_RELEASE,
                distributionDownloadFromMaven = true
            )
        )

    @DisplayName("KT-58303: Kotlin Native must not be downloaded during configuration phase")
    @GradleTest
    fun shouldNotDownloadKotlinNativeOnConfigurationPhase(gradleVersion: GradleVersion, @TempDir konanTemp: Path) {
        nativeProject(
            "native-simple-project",
            gradleVersion,
            buildOptions = defaultBuildOptions.copy(
                konanDataDir = konanTemp,
            ),
        ) {
            build("help") {
                assertOutputDoesNotContain(DOWNLOAD_KONAN_FINISHED_LOG)
                assertOutputDoesNotContain(UNPUCK_KONAN_FINISHED_LOG)
                assertOutputDoesNotContain("Please wait while Kotlin/Native")
            }
        }
    }

    @DisplayName("KT-58303: Kotlin Native must be downloaded during execution phase")
    @GradleTest
    fun shouldDownloadKotlinNativeOnExecutionPhase(gradleVersion: GradleVersion, @TempDir konanTemp: Path) {
        nativeProject(
            "native-simple-project",
            gradleVersion,
            buildOptions = defaultBuildOptions.copy(
                konanDataDir = konanTemp,
            ),
        ) {
            build("assemble") {
                assertOutputDoesNotContain(DOWNLOAD_KONAN_FINISHED_LOG)
                assertOutputContains(UNPUCK_KONAN_FINISHED_LOG)
                assertOutputDoesNotContain("Please wait while Kotlin/Native")
            }
        }
    }

    @DisplayName("KT-58303: Downloading Kotlin Native on configuration phase(deprecated version)")
    @GradleTest
    fun shouldDownloadKotlinNativeOnConfigurationPhaseWithToolchainDisabled(gradleVersion: GradleVersion, @TempDir konanTemp: Path) {
        nativeProject(
            "native-simple-project",
            gradleVersion,
            buildOptions = defaultBuildOptions.copy(
                konanDataDir = konanTemp,
                freeArgs = listOf("-Pkotlin.native.toolchain.enabled=false"),
            ),
        ) {
            build("assemble") {
                assertOutputContains(DOWNLOAD_KONAN_FINISHED_LOG)
                assertOutputContains("Please wait while Kotlin/Native")
            }
        }
    }

    @DisplayName("Checking multi-module native project with different path to kotlin native compiler in each module")
    @GradleTest
    fun multiModuleProjectWithDifferentKotlinNativeCompilers(gradleVersion: GradleVersion, @TempDir customNativeHomePath: Path) {
        nativeProject("native-multi-module-project", gradleVersion, configureSubProjects = true) {
            val defaultKotlinNativeHomePath =
                defaultBuildOptions.konanDataDir?.toAbsolutePath()?.normalize()
                    ?: error("Default konan data dir must be set in this test before overriding")

            // setting different konan home in different subprojects
            subProject("native1").gradleProperties.appendKonanToGradleProperties(customNativeHomePath.absolutePathString())
            subProject("native2").gradleProperties.appendKonanToGradleProperties(defaultKotlinNativeHomePath.absolutePathString())

            val buildOptions = defaultBuildOptions.copy(
                konanDataDir = null,
            )

            build("assemble", buildOptions = buildOptions) {

                // check that in first project we use k/n from custom konan location
                assertNativeTasksClasspath(":native1:compileKotlin${nativeHostTargetName.capitalize()}") {
                    val konanLibsPath = customNativeHomePath.resolve(STABLE_VERSION_DIR_NAME).resolve("konan").resolve("lib")
                    assertContains(it, konanLibsPath.resolve("kotlin-native-compiler-embeddable.jar").absolutePathString())
                    assertContains(it, konanLibsPath.resolve("trove4j.jar").absolutePathString())
                }

                // check that in second project we use k/n from default konan location
                assertNativeTasksClasspath(":native2:compileKotlin${nativeHostTargetName.capitalize()}") {
                    val konanLibsPath = defaultKotlinNativeHomePath.resolve(STABLE_VERSION_DIR_NAME).resolve("konan").resolve("lib")
                    assertContains(it, konanLibsPath.resolve("kotlin-native-compiler-embeddable.jar").absolutePathString())
                    assertContains(it, konanLibsPath.resolve("trove4j.jar").absolutePathString())
                }
            }
        }
    }

    private fun Path.appendKonanToGradleProperties(konanAbsolutePathString: String) {
        this.appendText(
            """
                    
            kotlin.native.home=${konanAbsolutePathString}
            konan.data.dir=${konanAbsolutePathString}
            """.trimIndent()
        )
    }
}