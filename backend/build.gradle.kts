import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.3.50"
//    id("com.github.johnrengelman.shadow")
    application
}

application {
    mainClassName = "com.halcyonmobile.multiplatformplayground.backend.ServerKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":commonModel"))
    implementation("io.ktor:ktor-client-apache:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-json-jvm:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-serialization:${project.extra["ktorVersion"]}")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.extra["kotlinVersion"]}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${project.extra["kotlinVersion"]}")
    implementation("io.ktor:ktor-server-netty:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-auth:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-websockets:${project.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-apache:${project.extra["ktorVersion"]}")

    // DI
    implementation("org.kodein.di:kodein-di-generic-jvm:${project.extra["kodeinVersion"]}")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:${project.extra["kodeinVersion"]}")

    implementation("org.jetbrains.exposed:exposed:0.17.7")
    implementation(group = "com.zaxxer", name = "HikariCP", version = "2.7.2")

}

sourceSets.main {
    java.srcDirs("src")
    resources.srcDirs("resources")
    withConvention(KotlinSourceSet::class) {
        kotlin.srcDirs("src")
    }
}
//tasks {
//    @Suppress("CAST_NEVER_SUCCEEDS")
//    named<ShadowJar>("shadowJar") {
//        archiveBaseName.set("backend")
//        archiveClassifier.set(null as? String)
//        archiveVersion.set(null as? String)
//    }
//}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.apply {
    apiVersion = "1.3"
    languageVersion = "1.3"
    jvmTarget = "1.8"
}