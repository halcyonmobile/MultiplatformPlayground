// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/icerockdev/plugins")
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha12")
        classpath(Versions.KOTLIN_GRADLE_PLUGIN)
        classpath(Versions.Common.KOIN_GRADLE_PLUGIN)
        classpath(Versions.Common.MOKO_RESOURCES_GRADLE_PLUGIN)
        classpath(Versions.Common.SQL_DELIGHT_GRADLE_PLUGIN)
        classpath(Versions.Common.BUILD_KONFIG_GRADLE_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/exposed")
        maven("https://jitpack.io")
        maven("https://dl.bintray.com/icerockdev/moko")
        maven("https://dl.bintray.com/ekito/koin") // Needed for some of the koin-mp metadata
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
