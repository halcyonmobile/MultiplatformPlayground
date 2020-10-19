package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavouritesViewModel(get()) }
    viewModel { SettingsViewModel() }
    viewModel { (categoryId: Long) -> ApplicationsViewModel(categoryId, get()) }
    viewModel { (applicationId: Long) -> ApplicationDetailViewModel(applicationId, get(), get()) }
    viewModel { (initialCategoryId: Long) ->
        UploadApplicationViewModel(
            initialCategoryId,
            get(),
            get()
        )
    }
}