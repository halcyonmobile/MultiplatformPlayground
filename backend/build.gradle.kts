import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath(Versions.Jvm.SHADOW_GRADLE_PLUGIN)
    }
}

application {
    // TODO to solve deprecation checkout https://github.com/johnrengelman/shadow/issues/336
    mainClassName = "com.halcyonmobile.multiplatformplayground.ServerKt"
}

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.KOTLIN_VERSION
    application
    id("com.github.johnrengelman.shadow") version Versions.Jvm.SHADOW_JAR_VERSION
}


dependencies {
    implementation(project(":commonModel"))
    implementation(Versions.Jvm.STANDARD_LIBRARY)
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
    implementation(Versions.Jvm.POSTGRESQL)
    implementation(Versions.Jvm.HIKARI_CONNECTION_POOL)

    implementation(Versions.Jvm.LOGBACK)

    implementation(Versions.Jvm.AWS_JAVA_SDK)
}

tasks.withType<ShadowJar> {
    archiveBaseName.set("Backend")
    archiveClassifier.set("")
    archiveVersion.set("")
    isZip64 = true
}