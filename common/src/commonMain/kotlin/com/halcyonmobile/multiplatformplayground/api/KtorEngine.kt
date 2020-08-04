package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.HttpClientConfig

internal expect val installNetworkLogger: HttpClientConfig<*>.() -> Unit

expect val log: (String) -> Unit