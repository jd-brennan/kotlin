plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    embedded(project(":plugins:jso:jso-compiler")) { isTransitive = false }
}

publish()

runtimeJar(rewriteDefaultJarDepsToShadedCompiler())
sourcesJarWithSourcesFromEmbedded(
    project(":plugins:jso:jso-compiler").tasks.named<Jar>("sourcesJar")
)
javadocJarWithJavadocFromEmbedded(
    project(":plugins:jso:jso-compiler").tasks.named<Jar>("javadocJar")
)
