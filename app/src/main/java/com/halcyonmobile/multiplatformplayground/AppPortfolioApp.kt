package com.halcyonmobile.multiplatformplayground

import android.app.Application
import com.halcyonmobile.multiplatformplayground.di.bindCommonModule
import com.halcyonmobile.multiplatformplayground.di.bindViewModelModule
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Appearance
import com.pandulapeter.beagle.common.configuration.Behavior
import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.logKtor.BeagleKtorLogger
import com.pandulapeter.beagle.modules.AnimationDurationSwitchModule
import com.pandulapeter.beagle.modules.AppInfoButtonModule
import com.pandulapeter.beagle.modules.DeveloperOptionsButtonModule
import com.pandulapeter.beagle.modules.DeviceInfoModule
import com.pandulapeter.beagle.modules.DividerModule
import com.pandulapeter.beagle.modules.HeaderModule
import com.pandulapeter.beagle.modules.KeylineOverlaySwitchModule
import com.pandulapeter.beagle.modules.LogListModule
import com.pandulapeter.beagle.modules.NetworkLogListModule
import com.pandulapeter.beagle.modules.ScreenCaptureToolboxModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

@Suppress("unused")
class AppPortfolioApp : Application(), KodeinAware {

    override val kodein = Kodein {
        bindCommonModule()
        bindViewModelModule()
    }

    override fun onCreate() {
        super.onCreate()
        @Suppress("ConstantConditionIf")
        if (BuildConfig.BUILD_TYPE != "release") {
            Beagle.initialize(
                application = this,
                appearance = Appearance(
                    themeResourceId = R.style.BaseTheme_App
                ),
                behavior = Behavior(
                    logger = BeagleLogger,
                    networkLoggers = listOf(BeagleKtorLogger)
                )
            )
            Beagle.set(
                HeaderModule(
                    title = getString(R.string.app_name),
                    subtitle = BuildConfig.APPLICATION_ID,
                    text = "${BuildConfig.BUILD_TYPE} v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
                ),
                AppInfoButtonModule(),
                DeveloperOptionsButtonModule(),
                KeylineOverlaySwitchModule(),
                AnimationDurationSwitchModule(),
                DividerModule(),
                ScreenCaptureToolboxModule(),
                NetworkLogListModule(), //TODO: Add baseUrl parameter
                LogListModule(),
                DeviceInfoModule()
            )
        }
    }
}