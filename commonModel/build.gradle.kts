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
        implementation("org.jetbrains.kotlin:kotlin-stdlib:${project.extra["kotlinVersion"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${project.extra["serializationVersion"]}")
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${project.extra["serializationVersion"]}")
            }
        }
    }

    sourceSets["backendMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:${project.extra["kotlinVersion"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${project.extra["serializationVersion"]}")
    }
}