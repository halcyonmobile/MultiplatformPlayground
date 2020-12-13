plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
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
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerVersion = "1.4.20"
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER_VERSION
    }
    lintOptions {
        disable("InvalidFragmentVersionForActivityResult")
    }
}

dependencies {
    implementation(project(":common"))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Versions.Android.STANDARD_LIBRARY)

    implementation(Versions.Android.COMPOSE_UI)
    implementation(Versions.Android.COMPOSE_TOOLING)
    implementation(Versions.Android.COMPOSE_FOUNDATION)
    implementation(Versions.Android.COMPOSE_MATERIAL_DESIGN)
    implementation(Versions.Android.COMPOSE_MD_ICONS_CORE)
    implementation(Versions.Android.COMPOSE_MD_ICONS_EXTENDED)
    implementation(Versions.Android.COMPOSE_RUNTIME)
    implementation(Versions.Android.COMPOSE_THEME_ADAPTER)
    implementation(Versions.Android.NAVIGATION_COMPOSE)
    implementation(Versions.Android.COIL_COMPOSE)
    implementation(Versions.Android.INSETS_COMPOSE)

    implementation(Versions.Android.APP_COMPAT)
    implementation(Versions.Android.KOTLIN_EXTENSIONS)
    implementation(Versions.Android.CONSTRAINT_LAYOUT)
    implementation(Versions.Android.MATERIAL_COMPONENTS)

    implementation(Versions.Android.KOIN_ANDROID_VIEWMODEL)

    implementation(Versions.Android.LIFECYCLE_EXTENSIONS)
    implementation(Versions.Android.LIVE_DATA)

    debugImplementation(Versions.Android.BEAGLE_DRAWER)
    releaseImplementation(Versions.Android.BEAGLE_NOOP)
}