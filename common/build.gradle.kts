import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
    id("com.android.library")
    id("org.jetbrains.kotlin.native.cocoapods")
    id("com.squareup.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.codingfeline.buildkonfig")
}

version = "1.0.0"

kotlin {
    android()

    macosX64("macOS")

    // Set the target based on if it's real phone ore simulator
    if (System.getenv("SDK_NAME").orEmpty().startsWith("iphoneos")) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }
    .apply {
        compilations {
            val main by getting {
                kotlinOptions.freeCompilerArgs =
                    listOf("-Xobjc-generics", "-Xopt-in=kotlin.RequiresOptIn")
            }
        }
    }
    cocoapods {
        summary = "AppPortfolio common code"
        homepage = "AppPortfolio home page"
    }
    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(project(":commonModel"))
                // Ktor-client for network requests
                implementation(Versions.Common.KTOR_CLIENT_CORE)
                api(Versions.Common.KTOR_LOGGING)
                implementation(Versions.Common.KTOR_CLIENT_JSON)
                implementation(Versions.Common.KTOR_CLIENT_SERIALIZATION)

                implementation(Versions.Common.COROUTINES_CORE) {
                    // Using strictly causes an issue, didn't investigate it yet
                    // https://kotlinlang.slack.com/archives/C1CFAFJSK/p1603044902445900
                    isForce = true
                }
                implementation(Versions.Common.SERIALIZATION)
                // Koin DI
                implementation(Versions.Common.KOIN_CORE)
                // Debug menu
                api(Versions.Common.BEAGLE_LOG)
                api(Versions.Common.BEAGLE_LOG_KTOR)
                // Multiplatform resources
                api(Versions.Common.MOKO_RESOURCES)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Versions.Android.VIEW_MODEL)
                implementation(Versions.Android.KOTLIN_EXTENSIONS)

                // Ktor-client for network requests
                implementation(Versions.Android.KTOR_CLIENT)

                implementation(Versions.Android.SQL_DELIGHT_DRIVER)
            }
        }
        val appleMain by creating {
            dependsOn(commonMain)
            // TODO Other common dependencies should be added here
        }
        val iosMain by getting {
            dependsOn(appleMain)
            dependencies {
                implementation(Versions.Apple.KTOR_CLIENT)
                implementation(Versions.Apple.SQL_DELIGHT_DRIVER)
            }
        }
        val macOSMain by getting {
            dependsOn(appleMain)
            dependencies {
                implementation(Versions.Apple.KTOR_CLIENT)
                implementation(Versions.Apple.SQL_DELIGHT_DRIVER)
            }
        }
    }
}

android {
    compileSdkVersion(Versions.Android.SDK_VERSION)
    buildToolsVersion(Versions.Android.BUILD_TOOLS_VERSION)
    defaultConfig {
        minSdkVersion(Versions.Android.MINIMUM_SDK_VERSION)
        targetSdkVersion(Versions.Android.SDK_VERSION)
    }
    /*
    There seems to be an issue with the latest canary (7.0.0-alpha03) Android studio.
    Revisit this workaround once the issue is patched.

    https://youtrack.jetbrains.com/issue/KT-43944
     */
    configurations {
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            java.srcDirs("src/androidMain/kotlin")
            res.srcDirs("src/androidMain/res")
        }
    }
    lintOptions {
        disable("InvalidFragmentVersionForActivityResult")
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val target = if(System.getenv("SDK_NAME").orEmpty().startsWith("macosx")) "macOS" else "ios"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(target).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    with(kotlinOptions) {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

sqldelight {
    database("MultiplatformDatabase") { // This will be the name of the generated database class.
        packageName = "com.halcyonmobile.multiplatformplayground.db"
        sourceFolders = listOf("sqldelight")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.halcyonmobile.multiplatformplayground" // required
}

buildkonfig {
    packageName = "com.halcyonmobile.multiplatformplayground"
    val baseUrl = "baseUrl"
    defaultConfigs {
        buildConfigField(
            Type.STRING,
            baseUrl,
            "https://halcyon-multiplatform-backend.herokuapp.com/"
        )
    }
    defaultConfigs("dev") {
        buildConfigField(Type.STRING, baseUrl, "http://0.0.0.0:8080/")
    }
}