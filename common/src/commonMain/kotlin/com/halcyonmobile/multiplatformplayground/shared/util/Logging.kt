package com.halcyonmobile.multiplatformplayground.shared.util

import io.ktor.client.HttpClientConfig

internal expect val installNetworkLogger: HttpClientConfig<*>.() -> Unit

expect val log: (String) -> Unit