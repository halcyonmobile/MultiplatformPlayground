plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Versions.Android.sdkVersion)

    defaultConfig {
        applicationId = "com.halcyonmobile.multiplatformplayground"
        minSdkVersion(Versions.Android.minSdkVersion)
        targetSdkVersion(Versions.Android.sdkVersion)
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
    implementation(Versions.Android.stdlib)

    implementation(Versions.Android.appCompat)
    implementation(Versions.Android.ktx)
    implementation(Versions.Android.constraintLayout)
    implementation(Versions.Android.materialComponents)

    implementation(Versions.Android.kodeinGeneric)
    implementation(Versions.Android.kodeinAndroidX)

    implementation(Versions.Android.navigationFragment)
    implementation(Versions.Android.navigationUi)

    implementation(Versions.Android.lifecycleExtensions)
    implementation(Versions.Android.liveData)

    debugImplementation(Versions.Android.beagleDrawer)
    releaseImplementation(Versions.Android.beagleNoop)
}