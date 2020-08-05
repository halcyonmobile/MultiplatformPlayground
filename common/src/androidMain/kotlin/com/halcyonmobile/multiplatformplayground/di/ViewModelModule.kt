package com.halcyonmobile.multiplatformplayground.di

import androidx.lifecycle.ViewModelProvider
import com.halcyonmobile.multiplatformplayground.shared.util.bindViewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.FavouritesViewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.SettingsViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

private val viewModelModule = Kodein.Module(name = "viewModelModule") {
    bindViewModel<HomeViewModel>() with provider {
        HomeViewModel(instance(), instance(), instance())
    }
    bindViewModel<FavouritesViewModel>() with provider { FavouritesViewModel(instance()) }
    bindViewModel<SettingsViewModel>() with provider { SettingsViewModel() }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
}

fun Kodein.MainBuilder.bindViewModelModule() {
    import(viewModelModule)
}