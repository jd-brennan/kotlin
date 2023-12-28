plugins {
    kotlin("multiplatform")
}

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    jvm().compilations.all {
        compilerOptions.configure {
            verbose.convention(true)
        }
    }
}
