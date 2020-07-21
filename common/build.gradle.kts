import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
}

version = "1.0.0"

android {
    // todo extract these
    compileSdkVersion(Versions.Android.SDK_VERSION)
    buildToolsVersion(Versions.Android.BUILD_TOOLS_VERSION)
    defaultConfig {
        minSdkVersion(Versions.Android.MINIMUM_SDK_VERSION)
        targetSdkVersion(Versions.Android.SDK_VERSION)
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
    // Select iOS target platform depending on the Xcode environment variables
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

    cocoapods {
        summary = "AppPortfolio common code"
        homepage = "AppPortfolio home page"
    }

    android()

    // region Common
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":commonModel"))
                // Ktor-client for network requests
                implementation(Versions.Common.KTOR_CLIENT_CORE)
                implementation(Versions.Common.KTOR_LOGGING)
                implementation(Versions.Common.KTOR_CLIENT_JSON)
                implementation(Versions.Common.KTOR_CLIENT_SERIALIZATION)
                implementation(Versions.Common.KTOR_UTILITY)

                implementation(Versions.Common.STANDARD_LIBRARY)
                implementation(Versions.Common.COROUTINES_CORE)
                implementation(Versions.Common.SERIALIZATION)
                // DI
                implementation(Versions.Shared.KODEIN_CORE)
                implementation(Versions.Shared.KODEIN_ERASED)
            }
        }
    }
    // endregion

    // region Android
    android.sourceSets.forEach { _ ->
        dependencies {
            implementation(Versions.Shared.STANDARD_LIBRARY)
            implementation(Versions.Android.LIFECYCLE_EXTENSIONS)
            implementation(Versions.Android.LIVE_DATA)
            implementation(Versions.Android.VIEW_MODEL)

            // Ktor-client for network requests
            implementation(Versions.Jvm.KTOR_CLIENT_SERIALIZATION)
            implementation(Versions.Jvm.KTOR_CLIENT_CORE)
            implementation(Versions.Android.KTOR_CLIENT)
            implementation(Versions.Jvm.KTOR_CLIENT_JSON)
            implementation(Versions.Jvm.KTOR_LOGGING)
            // Serialization
            implementation(Versions.Shared.SERIALIZATION_RUNTIME)

            implementation(Versions.Jvm.KODEIN_GENERIC)
            implementation(Versions.Android.KODEIN_ANDROID_X)
        }
    }
    //endregion

    // region iOS
    sourceSets["iosMain"].dependencies {
        implementation(Versions.iOS.STANDARD_LIBRARY)
        implementation(Versions.iOS.COROUTINES)

        implementation(Versions.iOS.KTOR_CLIENT)
        implementation(Versions.iOS.KTOR_CLIENT_JSON)
        implementation(Versions.iOS.KTOR_SERIALIZATION)
        implementation(Versions.iOS.KTOR_LOGGING)
    }
    // endregion
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    with(kotlinOptions) {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}