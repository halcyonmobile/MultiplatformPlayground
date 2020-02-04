// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.3")
        classpath(Versions.kotlinGradlePlugin)
        classpath(Versions.kotlinSerializationPlugin)
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Android.navigationVersion}")
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
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
