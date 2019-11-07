plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.50"
    application
}

application {
    // TODO add main class name
    mainClassName = ""
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
//    implementation(project(":common"))

}