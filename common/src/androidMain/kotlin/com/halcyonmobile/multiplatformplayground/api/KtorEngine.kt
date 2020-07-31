package com.halcyonmobile.multiplatformplayground.api

import android.util.Log
import com.pandulapeter.beagle.log.BeagleLogger
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android

internal actual val engine by lazy { Android.create() }

internal actual val installNetworkLogger: HttpClientConfig<*>.() -> Unit = { install(BetterBeagleLogger) }

actual val log: (String) -> Unit = {
    BeagleLogger.log(it)
    Log.d("AppPortfolioLog", it)
}