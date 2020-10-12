package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel() }
}