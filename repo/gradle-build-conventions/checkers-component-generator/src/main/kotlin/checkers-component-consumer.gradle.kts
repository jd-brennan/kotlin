plugins {
    kotlin("jvm")
}

val generatorClasspath: Configuration by configurations.creating

dependencies {
    generatorClasspath(project(":compiler:fir:checkers:checkers-component-generator"))
}

val generateCheckersComponents by tasks.registering(JavaExec::class) {
    workingDir = rootDir
    classpath = generatorClasspath
    mainClass.set("org.jetbrains.kotlin.fir.checkers.generator.MainKt")
    systemProperties["line.separator"] = "\n"

    val generationRoot = layout.projectDirectory.dir("gen")
    args(project.name, generationRoot)
    outputs.dir(generationRoot)
}

sourceSets {
    named("main") {
        java.srcDirs(generateCheckersComponents)
    }
}
