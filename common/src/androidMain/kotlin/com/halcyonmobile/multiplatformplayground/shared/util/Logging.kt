package com.halcyonmobile.multiplatformplayground.shared.util

import android.util.Log
import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.logKtor.BeagleKtorLogger
import io.ktor.client.HttpClientConfig
import io.ktor.client.features.HttpClientFeature

internal actual val installNetworkLogger: HttpClientConfig<*>.() -> Unit = {
    (BeagleKtorLogger.logger as? HttpClientFeature<*, *>?)?.let { install(it) }
}

actual val log: (String) -> Unit = {
    BeagleLogger.log(it)
    Log.d("AppPortfolioLog", it)
}