package com.halcyonmobile.multiplatformplayground

import android.app.Application
import com.halcyonmobile.multiplatformplayground.api.KtorApi
import com.halcyonmobile.multiplatformplayground.di.appModule
import com.halcyonmobile.multiplatformplayground.di.getCommonModules
import com.halcyonmobile.multiplatformplayground.di.viewModelModule
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Appearance
import com.pandulapeter.beagle.common.configuration.Behavior
import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.logKtor.BeagleKtorLogger
import com.pandulapeter.beagle.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppPortfolioApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupDebugMenu()
        startKoin {
            androidContext(this@AppPortfolioApp)
            modules(getCommonModules(this@AppPortfolioApp) + appModule + viewModelModule)
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
                NetworkLogListModule(baseUrl = KtorApi.BASE_URL),
                LogListModule(),
                LifecycleLogListModule(),
                DividerModule(),
                SectionHeaderModule("Other"),
                DeviceInfoModule()
            )
        }
    }
}