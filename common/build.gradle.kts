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
        minSdkVersion(19)
        targetSdkVersion(29)
    }

    packagingOptions {
        pickFirst("META-INF/ktor-client-serialization.kotlin_module")
        pickFirst("META-INF/kotlinx-coroutines-io.kotlin_module")
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
        binaries {
            framework {
                baseName = "common"
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
            // Serialization
            implementation(Versions.Shared.serializationRuntime)
        }
    }
    // endregion
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText(
            "#!/bin/bash\n"
                    + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                    + "cd '${rootProject.rootDir}'\n"
                    + "./gradlew \$@\n"
        )
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }