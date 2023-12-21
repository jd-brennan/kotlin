description = "Kotlin JavaScript Object Compiler Plugin (CLI)"

plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    compileOnly(project(":compiler:util"))
    compileOnly(project(":compiler:cli"))
    compileOnly(project(":compiler:plugin-api"))
    compileOnly(project(":compiler:fir:entrypoint"))

    implementation(project(":plugins:jso:jso-compiler:kotlinx-jso-compiler-plugin.backend"))
    implementation(project(":plugins:jso:jso-compiler:kotlinx-jso-compiler-plugin.common"))
    implementation(project(":plugins:jso:jso-compiler:kotlinx-jso-compiler-plugin.k2"))

    compileOnly(intellijCore())
}

sourceSets {
    "main" { projectDefault() }
    "test" { none() }
}

runtimeJar()
sourcesJar()
javadocJar()
