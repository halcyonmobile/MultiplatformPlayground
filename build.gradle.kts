// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    extra["kotlinVersion"] = "1.3.60"
    extra["shadowVersion"] = "2.0.2"

    repositories {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlinVersion"]}")
        classpath("com.github.jengelman.gradle.plugins:shadow:${project.extra["shadowVersion"]}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.2.0-rc04")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    project.extra.apply {
        // TODO extract kotlinVersion and shadowVersion
        set("shadowVersion", "2.0.2")
        set("kotlinVersion", "1.3.50")
        set("compileSdk", 28)
        set("minSdk", 21)
        set("targetSdk", 29)
        set("version", 1)
        set("versionName", "1.0.0")
    }
    repositories {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/kotlin/ktor")
        maven("https://dl.bintray.com/kotlin/exposed")
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/kodein-framework/Kodein-DI")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
