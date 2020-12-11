import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
}

version = "1.0.0"

kotlin {
    jvm("backend")

    if (System.getenv("SDK_NAME").orEmpty().startsWith("iphoneos")) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }.apply {
        compilations {
            val main by getting {
                kotlinOptions.freeCompilerArgs =
                    listOf("-Xobjc-generics", "-Xopt-in=kotlin.RequiresOptIn", "-Xinline-classes")
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Versions.Common.SERIALIZATION)
            }
        }
    }
}