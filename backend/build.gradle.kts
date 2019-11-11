import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.3.50"
    application
}

application {
    mainClassName = "com.h`alcyonmobile.multiplatformplayground.ServerKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    //    implementation(project(":common"))
    implementation("io.ktor:ktor-client-apache:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-json-jvm:${project.extra["ktorVersion"]}")

    implementation("io.ktor:ktor-server-netty:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-auth:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-websockets:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-apache:${project.extra["ktorVersion"]}")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.apply {
    apiVersion ="1.3"
    languageVersion = "1.3"
}