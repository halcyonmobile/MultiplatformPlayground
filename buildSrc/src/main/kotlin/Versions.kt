object Versions {
    const val KOTLIN_VERSION = "1.4.0-rc"
    private const val KTOR_VERSION = "1.3.2-1.4.0-rc"
    private const val COROUTINES_VERSION = "1.3.8-native-mt-1.4.0-rc"
    private const val SERIALIZATION_VERSION = "1.0-M1-1.4.0-rc"
    private const val KODEIN_VERSION = "6.5.1"
    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    const val KOTLIN_SERIALIZATION_PLUGIN = "org.jetbrains.kotlin:kotlin-serialization:$KOTLIN_VERSION"
    private const val BEAGLE_VERSION = "2.0.0-beta13"

    object Common {
        const val KTOR_CLIENT_CORE = "io.ktor:ktor-client-core:$KTOR_VERSION"
        const val KTOR_CLIENT_JSON = "io.ktor:ktor-client-json:$KTOR_VERSION"
        const val KTOR_LOGGING = "io.ktor:ktor-client-logging:$KTOR_VERSION"
        const val KTOR_CLIENT_SERIALIZATION = "io.ktor:ktor-client-serialization:$KTOR_VERSION"
        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"

        const val SERIALIZATION =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$SERIALIZATION_VERSION"
        const val BEAGLE_LOG = "com.github.pandulapeter.beagle:log:$BEAGLE_VERSION"
        const val BEAGLE_LOG_KTOR = "com.github.pandulapeter.beagle:log-ktor:$BEAGLE_VERSION"
    }

    object Jvm {
        const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$KOTLIN_VERSION"

        const val KTOR_AUTH = "io.ktor:ktor-auth:$KTOR_VERSION"
        const val KTOR_WEB_SOCKETS = "io.ktor:ktor-websockets:$KTOR_VERSION"
        const val KTOR_CLIENT_APACHE = "io.ktor:ktor-client-apache:$KTOR_VERSION"
        const val KTOR_SERVER_NETTY = "io.ktor:ktor-server-netty:$KTOR_VERSION"
        const val KTOR_SERIALIZATION = "io.ktor:ktor-serialization:$KTOR_VERSION"

        const val KODEIN_GENERIC = "org.kodein.di:kodein-di-generic-jvm:$KODEIN_VERSION"
        const val KODEIN_KTOR_SERVER = "org.kodein.di:kodein-di-framework-ktor-server-jvm:$KODEIN_VERSION"

        const val JETBRAINS_EXPOSED_CORE = "org.jetbrains.exposed:exposed-core:0.23.1"
        const val JETBRAINS_EXPOSED_DAO = "org.jetbrains.exposed:exposed-dao:0.23.1"
        const val JETBRAINS_EXPOSED_JDBC = "org.jetbrains.exposed:exposed-jdbc:0.23.1"

        const val H2_DATABASE = "com.h2database:h2:1.4.200"
        const val HIKARI_CONNECTION_POOL = "com.zaxxer:HikariCP:3.4.5"
        const val LOGBACK = "ch.qos.logback:logback-classic:1.2.3"
    }

    object Android {
        const val SDK_VERSION = 29
        const val MINIMUM_SDK_VERSION = 23
        const val BUILD_TOOLS_VERSION = "29.0.2"

        const val STANDARD_LIBRARY = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"
        const val APP_COMPAT = "androidx.appcompat:appcompat:1.1.0"
        const val KOTLIN_EXTENSIONS = "androidx.core:core-ktx:1.1.0"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val MATERIAL_COMPONENTS = "com.google.android.material:material:1.2.0-alpha04"

        private const val LIFECYCLE_VERSION = "2.2.0"
        const val LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION"
        const val LIVE_DATA = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"
        const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"

        const val KODEIN_GENERIC = "org.kodein.di:kodein-di-generic-jvm:$KODEIN_VERSION"
        const val KODEIN_ANDROID_X = "org.kodein.di:kodein-di-framework-android-x:$KODEIN_VERSION"

        const val NAVIGATION_VERSION = "2.2.0"
        const val NAVIGATON_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"

        const val KTOR_CLIENT = "io.ktor:ktor-client-android:$KTOR_VERSION"

        const val BEAGLE_DRAWER = "com.github.pandulapeter.beagle:ui-drawer:$BEAGLE_VERSION"
        const val BEAGLE_NOOP = "com.github.pandulapeter.beagle:noop:$BEAGLE_VERSION"
    }
}