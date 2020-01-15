import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.3.50"
    application
}

application {
    mainClassName = "com.halcyonmobile.multiplatformplayground.backend.ServerKt"
}

task("stage"){
    dependsOn("installDist")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":commonModel"))
    implementation(Versions.Jvm.ktorClientApache)
    implementation(Versions.Jvm.ktorClientJson)
    implementation(Versions.Jvm.ktorSerialization)

    implementation(Versions.Jvm.stdlib)
    implementation(Versions.Jvm.kotlinReflect)
    implementation(Versions.Jvm.ktorServerNetty)
    implementation(Versions.Jvm.ktorAuth)
    implementation(Versions.Jvm.ktorWebSockets)
    implementation(Versions.Jvm.ktorClientApache)

    // DI
    implementation(Versions.Jvm.kodeinGeneric)
    implementation(Versions.Jvm.kodeinKtorServer)

    implementation(Versions.Jvm.jetbrainsExposed)
    implementation(group = "com.zaxxer", name = "HikariCP", version = "2.7.2")

    implementation(Versions.Jvm.googleCloudStorage)
    implementation(Versions.Jvm.logback)
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