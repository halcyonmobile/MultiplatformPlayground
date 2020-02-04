object Versions {
    const val kotlinVersion = "1.3.50"
    private const val ktorVersion = "1.2.5"
    private const val coroutinesVersion = "1.3.2"
    private const val serializationVersion = "0.13.0"
    private const val kodeinVersion = "6.4.1"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val kotlinSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"

    object Shared {
        const val kodeinCore = "org.kodein.di:kodein-di-core:$kodeinVersion"
        const val kodeinErased = "org.kodein.di:kodein-di-erased:$kodeinVersion"

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
        const val serializationRuntime =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization:$serializationVersion"

        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core$coroutinesVersion"
    }

    object Common {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-common:$kotlinVersion"

        const val ktorUtility = "io.ktor:ktor-utils-native:$ktorVersion"
        const val ktorClientCore = "io.ktor:ktor-client-core:$ktorVersion"
        const val ktorClientJson = "io.ktor:ktor-client-json:$ktorVersion"
        const val ktorLogging = "io.ktor:ktor-client-logging:$ktorVersion"
        const val ktorClientSerialization = "io.ktor:ktor-client-serialization:$ktorVersion"

        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion"
    }

    object Jvm {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"

        const val ktorAuth = "io.ktor:ktor-auth:$ktorVersion"
        const val ktorWebSockets = "io.ktor:ktor-websockets:$ktorVersion"
        const val ktorClientApache = "io.ktor:ktor-client-apache:$ktorVersion"
        const val ktorClientCore = "io.ktor:ktor-client-core-jvm:$ktorVersion"
        const val ktorClientJson = "io.ktor:ktor-client-json-jvm:$ktorVersion"
        const val ktorLogging = "io.ktor:ktor-client-logging-jvm:$ktorVersion"
        const val ktorClientSerialization = "io.ktor:ktor-client-serialization-jvm:$ktorVersion"
        const val ktorServerNetty = "io.ktor:ktor-server-netty:$ktorVersion"
        const val ktorSerialization = "io.ktor:ktor-serialization:$ktorVersion"

        const val kodeinGeneric = "org.kodein.di:kodein-di-generic-jvm:$kodeinVersion"
        const val kodeinKtorServer =
            "org.kodein.di:kodein-di-framework-ktor-server-jvm:$kodeinVersion"
        const val jetbrainsExposed = "org.jetbrains.exposed:exposed:0.17.7"

        const val googleCloudStorage = "com.google.cloud:google-cloud-storage:1.102.0"
        const val logback = "ch.qos.logback:logback-classic:1.2.3"
    }

    object iOS {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutinesVersion"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationVersion"

        const val ktorClient = "io.ktor:ktor-client-ios:$ktorVersion"
        const val ktorClientJson = "io.ktor:ktor-client-json-native:$ktorVersion"
        const val ktorSerialization = "io.ktor:ktor-client-serialization-native:$ktorVersion"
        const val ktorLogging = "io.ktor:ktor-client-logging-native:$ktorVersion"
    }

    object Android {
        const val sdkVersion = 29
        const val minSdkVersion = 21
        const val buildToolsVersion = "29.0.1"

        private const val lifecycleVersion = "2.2.0-alpha05"
        const val navigationVersion = "2.2.0"

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        const val appCompat = "androidx.appcompat:appcompat:1.1.0"
        const val ktx = "androidx.core:core-ktx:1.1.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val materialComponents = "com.google.android.material:material:1.2.0-alpha04"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"

        const val kodeinGeneric = "org.kodein.di:kodein-di-generic-jvm:$kodeinVersion"
        const val kodeinAndroidX = "org.kodein.di:kodein-di-framework-android-x:$kodeinVersion"

        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"

        const val androidEngine = "io.ktor:ktor-client-android:$ktorVersion"
    }
}