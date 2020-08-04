package com.halcyonmobile.multiplatformplayground.shared.util

import io.ktor.client.HttpClientConfig
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.SIMPLE

internal actual val installNetworkLogger: HttpClientConfig<*>.() -> Unit = {
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }
}

actual val log: (String) -> Unit = { println(it) }