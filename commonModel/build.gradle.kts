plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.50"
}

kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit) -> org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }

    jvm("backend")

    sourceSets["iosMain"].dependencies {
        implementation(Versions.Shared.stdlib)
        implementation(Versions.Shared.serializationRuntime)
    }

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
}