// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        maven("https://dl.bintray.com/icerockdev/plugins")
        mavenCentral()
    }
    dependencies {
        classpath("xml-apis:xml-apis:1.4.01") // TODO workaround for newest AS Canary related issue
        classpath("com.android.tools.build:gradle:4.2.0-alpha14")
        classpath(Versions.KOTLIN_GRADLE_PLUGIN)
        classpath(Versions.KOTLIN_SERIALIZATION_PLUGIN)
        classpath(Versions.Android.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN)
        classpath(Versions.Common.MOKO_RESOURCES_GRADLE_PLUGIN)
        classpath(Versions.SQL_DELIGHT_GRADLE_PLUGIN)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/kotlin/ktor")
        maven("https://dl.bintray.com/kotlin/exposed")
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/kodein-framework/Kodein-DI")
        maven("https://dl.bintray.com/icerockdev/moko")
        maven("https://dl.bintray.com/ekito/koin") // Needed for some of the koin-mp metadata
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
