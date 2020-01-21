package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.engine.ios.IosClientEngine

internal actual val engine by lazy { IosClientEngine }