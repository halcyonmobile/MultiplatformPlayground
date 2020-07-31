package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.engine.ios.Ios
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.SIMPLE

internal actual val engine by lazy { Ios.create() }

internal actual val networkLogger by lazy { Logger.SIMPLE }

actual val log: (String) -> Unit = { println(it) }