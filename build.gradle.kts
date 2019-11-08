// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    extra["kotlinVersion"] = "1.3.50"

    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlinVersion"]}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    project.extra.apply {
        set("compileSdk", 28)
        set("minSdk", 21)
        set("targetSdk", 27)
        set("version", 1)
        set("versionName", "1.0.0")
        set("ktorVersion", "1.1.3")
        set("coroutinesVersion", "1.3.0")
        set("serializationVersion", "0.10.0")
        set("kodeinVersion", "6.1.0")
    }
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
