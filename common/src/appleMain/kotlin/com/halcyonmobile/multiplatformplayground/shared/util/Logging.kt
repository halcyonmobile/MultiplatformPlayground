package com.halcyonmobile.multiplatformplayground.shared.util

import io.ktor.client.*
import io.ktor.client.features.logging.*

internal actual val installNetworkLogger: HttpClientConfig<*>.() -> Unit = {
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}

actual val log: (String) -> Unit = { println(it) }