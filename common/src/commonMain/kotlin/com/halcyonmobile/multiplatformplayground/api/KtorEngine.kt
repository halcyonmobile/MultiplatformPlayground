package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.logging.Logger

internal expect val engine: HttpClientEngine

internal expect val networkLogger: Logger

expect val log: (String) -> Unit