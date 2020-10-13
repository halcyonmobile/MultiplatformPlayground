package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.viewmodel.FavouritesViewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.SettingsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavouritesViewModel(get()) }
    viewModel { SettingsViewModel() }
}