plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
    application
}

application {
    mainClass.set("com.halcyonmobile.multiplatformplayground.ServerKt")
}

dependencies {
    implementation(project(":commonModel"))
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)
    implementation(Versions.Jvm.KTOR_SERIALIZATION)

    implementation(Versions.Jvm.KOTLIN_REFLECT)
    implementation(Versions.Jvm.KTOR_SERVER_NETTY)
    implementation(Versions.Jvm.KTOR_AUTH)
    implementation(Versions.Jvm.KTOR_WEB_SOCKETS)
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)
    // DI
    implementation(Versions.Jvm.KOIN_KTOR)

    implementation(Versions.Jvm.JETBRAINS_EXPOSED_CORE)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_DAO)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_JDBC)
    implementation(Versions.Jvm.H2_DATABASE)
    implementation(Versions.Jvm.HIKARI_CONNECTION_POOL)

    implementation(Versions.Jvm.LOGBACK)
}
