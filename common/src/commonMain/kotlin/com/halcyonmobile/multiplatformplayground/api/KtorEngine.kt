package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine

internal expect val engine: HttpClientEngine

internal expect val installNetworkLogger: HttpClientConfig<*>.() -> Unit

expect val log: (String) -> Unit