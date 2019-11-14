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
    jvm("backend")

    // region common
    sourceSets {
        commonMain {
            dependencies {
                // Ktor-client for network requests
                implementation("io.ktor:ktor-client-core:${project.extra["ktorVersion"]}")
                implementation("io.ktor:ktor-client-json:${project.extra["ktorVersion"]}")
                implementation("io.ktor:ktor-client-serialization:${project.extra["ktorVersion"]}")


                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${project.extra["coroutinesVersion"]}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${project.extra["serializationVersion"]}")
                // DI
                implementation("org.kodein.di:kodein-di-core:${project.extra["kodeinVersion"]}")
                implementation("org.kodein.di:kodein-di-erased:${project.extra["kodeinVersion"]}")
            }
        }
    }
    // endregion

    // region android
    val lifecycleVersion = "2.2.0-alpha05"

    android.sourceSets.forEach { _ ->
        dependencies {
            implementation("org.jetbrains.kotlin:kotlin-stdlib")
            implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
            implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

            // Ktor-client for network requests
            implementation("io.ktor:ktor-client-serialization-jvm:${project.extra["ktorVersion"]}")
            // Serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${project.extra["serializationVersion"]}")
        }
    }
    // endregion
    sourceSets["backendMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:${project.extra["kotlinVersion"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${project.extra["coroutinesVersion"]}")

        implementation("io.ktor:ktor-server-netty:${project.extra["ktorVersion"]}")
        implementation("io.ktor:ktor-client-serialization-jvm:${project.extra["ktorVersion"]}")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${project.extra["serializationVersion"]}")


        implementation("io.ktor:ktor-client-core-jvm:${project.extra["ktorVersion"]}")
    }

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