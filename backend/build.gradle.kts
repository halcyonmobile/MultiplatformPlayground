import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("kotlinx-serialization")
    application
}

application {
    mainClassName = "com.halcyonmobile.multiplatformplayground.backend.ServerKt"
}

task("stage") {
    dependsOn("installDist")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":commonModel"))
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)
    implementation(Versions.Jvm.KTOR_CLIENT_JSON)
    implementation(Versions.Jvm.KTOR_SERIALIZATION)

    implementation(Versions.Jvm.KOTLIN_REFLECT)
    implementation(Versions.Jvm.KTOR_SERVER_NETTY)
    implementation(Versions.Jvm.KTOR_AUTH)
    implementation(Versions.Jvm.KTOR_WEB_SOCKETS)
    implementation(Versions.Jvm.KTOR_CLIENT_APACHE)

    // DI
    implementation(Versions.Jvm.KODEIN_GENERIC)
    implementation(Versions.Jvm.KODEIN_KTOR_SERVER)

    implementation(Versions.Jvm.JETBRAINS_EXPOSED_CORE)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_DAO)
    implementation(Versions.Jvm.JETBRAINS_EXPOSED_JDBC)
    implementation(Versions.Jvm.H2_DATABASE)
    implementation(Versions.Jvm.HIKARI_CONNECTION_POOL)

    implementation(Versions.Jvm.GOOGLE_CLOUD_STORAGE)
    implementation(Versions.Jvm.LOGBACK)
}

sourceSets.main {
    java.srcDirs("src")
    resources.srcDirs("resources")
    withConvention(KotlinSourceSet::class) {
        kotlin.srcDirs("src")
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.apply {
    apiVersion = "1.3"
    languageVersion = "1.3"
    jvmTarget = "1.8"
}