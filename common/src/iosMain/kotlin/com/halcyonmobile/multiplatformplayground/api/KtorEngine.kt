package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.engine.ios.Ios

internal actual val engine by lazy { Ios.create() }