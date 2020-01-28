import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
}

kotlin {
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

    jvm("backend")

    sourceSets {
        commonMain {
            dependencies {
                implementation(Versions.Common.stdlib)
                implementation(Versions.Common.serialization)
            }
        }
    }

    sourceSets["backendMain"].dependencies {
        implementation(Versions.Shared.stdlib)
        implementation(Versions.Shared.serializationRuntime)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Versions.iOS.stdlib)
        implementation(Versions.iOS.serialization)
    }
}