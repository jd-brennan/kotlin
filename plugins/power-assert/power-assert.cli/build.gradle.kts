description = "Kotlin Power-Assert Compiler Plugin (CLI)"

plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    compileOnly(project(":compiler:util"))
    compileOnly(project(":compiler:cli"))
    compileOnly(project(":compiler:plugin-api"))
    compileOnly(project(":compiler:fir:entrypoint"))

    implementation(project(":kotlin-power-assert-compiler-plugin.backend"))

    compileOnly(intellijCore())
}

optInToExperimentalCompilerApi()

sourceSets {
    "main" { projectDefault() }
    "test" { none() }
}

runtimeJar()
sourcesJar()
javadocJar()
