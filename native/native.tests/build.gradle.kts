import org.jetbrains.kotlin.konan.properties.resolvablePropertyList
import org.jetbrains.kotlin.konan.target.Distribution
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.KonanTarget

plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    testImplementation(kotlinStdlib())
    testImplementation(commonDependency("org.jetbrains.kotlin:kotlin-reflect")) { isTransitive = false }
    testImplementation(intellijCore())
    testImplementation(commonDependency("commons-lang:commons-lang"))
    testImplementation(commonDependency("org.jetbrains.teamcity:serviceMessages"))
    testImplementation(project(":kotlin-compiler-runner-unshaded"))
    testImplementation(projectTests(":compiler:tests-common"))
    testImplementation(projectTests(":compiler:tests-common-new"))
    testImplementation(projectTests(":compiler:test-infrastructure"))
    testImplementation(projectTests(":generators:test-generator"))
    testImplementation(project(":compiler:ir.serialization.native"))
    testImplementation(project(":compiler:fir:native"))
    testImplementation(project(":native:kotlin-native-utils"))
    testImplementation(project(":native:executors"))
    testImplementation(project(":kotlin-util-klib-abi"))
    testImplementation(projectTests(":kotlin-util-klib-abi"))
    testApi(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(commonDependency("org.jetbrains.kotlinx", "kotlinx-metadata-klib"))
    testImplementation(commonDependency("org.jetbrains.kotlinx", "kotlinx-coroutines-core")) { isTransitive = false }

    testRuntimeOnly(commonDependency("org.jetbrains.intellij.deps.fastutil:intellij-deps-fastutil"))
}

sourceSets {
    "main" { none() }
    "test" {
        projectDefault()
        generatedTestDir()
    }
}

testsJar {}

// Tasks that run different sorts of tests. Most frequent use case: running specific tests at TeamCity.
val infrastructureTest = nativeTest("infrastructureTest", "infrastructure")
val codegenBoxTest = nativeTest("codegenBoxTest", "codegen & !frontend-fir")
val codegenBoxK2Test = nativeTest("codegenBoxK2Test", "codegen & frontend-fir")
val stdlibTest = nativeTest("stdlibTest", "stdlib & !frontend-fir")
val stdlibK2Test = nativeTest("stdlibK2Test", "stdlib & frontend-fir")
val kotlinTestLibraryTest = nativeTest("kotlinTestLibraryTest", "kotlin-test & !frontend-fir")
val kotlinTestLibraryK2Test = nativeTest("kotlinTestLibraryK2Test", "kotlin-test & frontend-fir")
val partialLinkageTest = nativeTest("partialLinkageTest", "partial-linkage")
val cinteropTest = nativeTest("cinteropTest", "cinterop")
val debuggerTest = nativeTest("debuggerTest", "debugger")
val cachesTest = nativeTest("cachesTest", "caches")
val klibTest = nativeTest("klibTest", "klib")
val standaloneTest = nativeTest("standaloneTest", "standalone")

val Project.target: KonanTarget
    get() = findProperty("kotlin.internal.native.test.target")
        ?.let { KonanTarget.predefinedTargets[it] }
        ?: HostManager.host

val Project.cacheMode
    get() = findProperty("kotlin.internal.native.test.cacheMode")?.toString()

val Project.isCacheModeEverywhere
    get() = cacheMode == "STATIC_EVERYWHERE" || cacheMode == "STATIC_PER_FILE_EVERYWHERE"

val validPropertiesNames = listOf(
    "konan.home",
    "org.jetbrains.kotlin.native.home",
    "kotlin.native.home"
)
val Project.kotlinNativeDist
    get() = rootProject.currentKotlinNativeDist

val Project.currentKotlinNativeDist
    get() = file(validPropertiesNames.firstOrNull { hasProperty(it) }?.let { findProperty(it) } ?: "dist")

val distribution = Distribution(project.kotlinNativeDist.absolutePath)
val cacheableTargets = distribution.properties
    .resolvablePropertyList("cacheableTargets", HostManager.hostName)
    .map { KonanTarget.predefinedTargets.getValue(it) }
    .toSet()

val testTags = findProperty("kotlin.native.tests.tags")?.toString()
// Note: arbitrary JUnit tag expressions can be used in this property.
// See https://junit.org/junit5/docs/current/user-guide/#running-tests-tag-expressions
val test by nativeTest("test", testTags) {
    findProperty("kotlin.internal.native.test.nativeHome")?.let {
        val home = findProperty("kotlin.native.home")
        check(it == home) {
            "Value of `-Pkotlin.native.home=$home` must be equal to value of `-Pkotlin.internal.native.test.nativeHome=$it`"
        }
    }
    check(!isCacheModeEverywhere || target in cacheableTargets) {
        "No cache support for test target $target at host target ${HostManager.host}"
    }
    if (target != HostManager.host)
        dependsOn(":kotlin-native:${target.name}CrossDistRuntime")

    // TODO: maybe detect needed platforms by scanning import in test sources, like old testinfra does.
    val commonPlatformLibs = listOfNotNull(
        "posix",
        "zlib",
        "iconv".takeIf { target == HostManager.host },
    )
    val applePlatformLibs = listOfNotNull(
        "objc",
        "darwin",
        "Foundation",
        "CoreGraphics",
        "AppKit".takeIf { target.family == Family.OSX }, // only for tests interop_kt55653 and interop_objc_kt56402
        "UIKit".takeIf { target.family != Family.OSX }, // only for test interop_kt40426
    ).takeIf { target.family.isAppleFamily }.orEmpty()

    (commonPlatformLibs + applePlatformLibs).forEach {
        val cacheSuffix = if (isCacheModeEverywhere) "Cache" else ""
        dependsOn(":kotlin-native:platformLibs:${target.name}-$it$cacheSuffix")
    }
}

val generateTests by generator("org.jetbrains.kotlin.generators.tests.GenerateNativeTestsKt") {
    javaLauncher.set(project.getToolchainLauncherFor(JdkMajorVersion.JDK_11_0))
    dependsOn(":compiler:generateTestData")
}
