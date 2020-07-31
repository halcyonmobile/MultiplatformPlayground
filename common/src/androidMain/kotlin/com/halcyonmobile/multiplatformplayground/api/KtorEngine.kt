package com.halcyonmobile.multiplatformplayground.api

import android.util.Log
import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.logKtor.BeagleKtorLogger
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpClientFeature

internal actual val engine by lazy { Android.create() }

internal actual val installNetworkLogger: HttpClientConfig<*>.() -> Unit = {
    (BeagleKtorLogger.logger as? HttpClientFeature<*, *>?)?.let { install(it) }
}

actual val log: (String) -> Unit = {
    BeagleLogger.log(it)
    Log.d("AppPortfolioLog", it)
}