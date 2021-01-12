package com.halcyonmobile.multiplatformplayground

import android.app.Application
import com.halcyonmobile.multiplatformplayground.api.KtorApiImpl
import com.halcyonmobile.multiplatformplayground.di.initKoin
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Appearance
import com.pandulapeter.beagle.common.configuration.Behavior
import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.logKtor.BeagleKtorLogger
import com.pandulapeter.beagle.modules.AppInfoButtonModule
import com.pandulapeter.beagle.modules.DeveloperOptionsButtonModule
import com.pandulapeter.beagle.modules.DeviceInfoModule
import com.pandulapeter.beagle.modules.DividerModule
import com.pandulapeter.beagle.modules.HeaderModule
import com.pandulapeter.beagle.modules.KeylineOverlaySwitchModule
import com.pandulapeter.beagle.modules.LifecycleLogListModule
import com.pandulapeter.beagle.modules.LogListModule
import com.pandulapeter.beagle.modules.NetworkLogListModule
import com.pandulapeter.beagle.modules.PaddingModule
import com.pandulapeter.beagle.modules.ScreenCaptureToolboxModule
import com.pandulapeter.beagle.modules.SectionHeaderModule
import org.koin.android.ext.koin.androidContext

@Suppress("unused")
class AppPortfolioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDebugMenu()
        initKoin {
            androidContext(this@AppPortfolioApp)
        }
    }

    private fun setupDebugMenu() {
        @Suppress("ConstantConditionIf")
        if (BuildConfig.BUILD_TYPE != "release") {
            Beagle.initialize(
                application = this,
                appearance = Appearance(
                    themeResourceId = R.style.BaseTheme
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
                PaddingModule(),
                SectionHeaderModule("General"),
                KeylineOverlaySwitchModule(),
                ScreenCaptureToolboxModule(),
                DividerModule(),
                SectionHeaderModule("Logs"),
                NetworkLogListModule(baseUrl = KtorApiImpl.baseUrl),
                LogListModule(),
                LifecycleLogListModule(),
                DividerModule(),
                SectionHeaderModule("Other"),
                DeviceInfoModule()
            )
        }
    }
}