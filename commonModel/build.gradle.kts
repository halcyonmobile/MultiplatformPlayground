plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.3.50"
}

kotlin {
    jvm("backend")

    sourceSets {
        commonMain {
            dependencies {
                implementation(Versions.Common.stdlib)
                implementation(Versions.Common.serialization)
            }
        }
    }

    sourceSets["backendMain"].dependencies {
        implementation(Versions.Shared.stdlib)
        implementation(Versions.Shared.serializationRuntime)
    }
}