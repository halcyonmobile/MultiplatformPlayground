plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Versions.Android.SDK_VERSION)

    defaultConfig {
        applicationId = "com.halcyonmobile.multiplatformplayground"
        minSdkVersion(Versions.Android.MINIMUM_SDK_VERSION)
        targetSdkVersion(Versions.Android.SDK_VERSION)
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dataBinding.isEnabled = true

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }

    kotlinOptions.jvmTarget = "1.8"

}

dependencies {
    implementation(project(":common"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Versions.Android.STANDARD_LIBRARY)

    implementation(Versions.Android.APP_COMPAT)
    implementation(Versions.Android.KOTLIN_EXTENSIONS)
    implementation(Versions.Android.CONSTRAINT_LAYOUT)
    implementation(Versions.Android.MATERIAL_COMPONENTS)

    implementation(Versions.Android.KODEIN_GENERIC)
    implementation(Versions.Android.KODEIN_ANDROID_X)

    implementation(Versions.Android.NAVIGATON_FRAGMENT)
    implementation(Versions.Android.NAVIGATION_UI)

    implementation(Versions.Android.LIFECYCLE_EXTENSIONS)
    implementation(Versions.Android.LIVE_DATA)

    debugImplementation(Versions.Android.BEAGLE_DRAWER)
    releaseImplementation(Versions.Android.BEAGLE_NOOP)
}