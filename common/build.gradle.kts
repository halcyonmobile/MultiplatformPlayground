import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.50"
    id("com.android.library")
}

android {
    // todo extract these
    compileSdkVersion(project.extra.get("compileSdk") as Int)
    buildToolsVersion("29.0.1")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
    }
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
                kotlinOptions.freeCompilerArgs = listOf("-Xobjc-generics")
            }
        }
    }

    android()

    // region common
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":commonModel"))
                // Ktor-client for network requests
                implementation(Versions.Common.ktorClientCore)
                implementation(Versions.Common.ktorLogging)
                implementation(Versions.Common.ktorClientJson)
                implementation(Versions.Common.ktorClientSerialization)
                implementation(Versions.Common.ktorUtility)

                implementation(Versions.Common.stdlib)
                implementation(Versions.Common.coroutinesCore)
                implementation(Versions.Common.serialization)
                // DI
                implementation(Versions.Shared.kodeinCore)
                implementation(Versions.Shared.kodeinErased)
                // I/O library
                implementation(Versions.Common.okio)
            }
        }
    }
    // endregion

    // region android
    android.sourceSets.forEach { _ ->
        dependencies {
            implementation(Versions.Shared.stdlib)
            implementation(Versions.Android.lifecycleExtensions)
            implementation(Versions.Android.liveData)
            implementation(Versions.Android.viewModel)

            // Ktor-client for network requests
            implementation(Versions.Jvm.ktorClientSerialization)
            implementation(Versions.Jvm.ktorClientCore)
            implementation(Versions.Android.androidEngine)
            implementation(Versions.Jvm.ktorClientJson)
            implementation(Versions.Jvm.ktorLogging)
            // Serialization
            implementation(Versions.Shared.serializationRuntime)

            implementation(Versions.Jvm.kodeinGeneric)
            implementation(Versions.Android.kodeinAndroidX)
        }
    }

    ios {
        dependencies {
            implementation(Versions.iOS.stdlib)
            implementation(Versions.iOS.coroutines)
            implementation(Versions.iOS.serialization)

//            implementation(Versions.iOS.ktorClient)
            implementation(Versions.iOS.ktorClientJson)
            implementation(Versions.iOS.ktorSerialization)
            implementation(Versions.iOS.ktorLogging)
        }
    }
    // endregion
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }