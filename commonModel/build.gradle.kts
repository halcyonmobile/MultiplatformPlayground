import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

version = "1.0.0"

kotlin {
    jvm("backend")

    ios {
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