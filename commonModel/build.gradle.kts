import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

kotlin {
    jvm("backend")

    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        compilations {
            val main by getting {
                kotlinOptions.freeCompilerArgs = listOf("-Xobjc-generics", "-Xinline-classes")
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