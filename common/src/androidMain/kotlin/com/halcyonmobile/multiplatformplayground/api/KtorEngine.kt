package com.halcyonmobile.multiplatformplayground.api

import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.logKtor.BeagleKtorLogger
import io.ktor.client.engine.android.Android
import io.ktor.client.features.logging.Logger

internal actual val engine by lazy { Android.create() }

internal actual val networkLogger: Logger by lazy { BeagleKtorLogger.logger as Logger }

actual val log: (String) -> Unit = { BeagleLogger.log(it) }