package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.storage.LocalSourceImpl
import io.ktor.application.*
import org.koin.dsl.module

internal fun Application.getKoinModule() = module {
    single<LocalSource> { LocalSourceImpl(this@getKoinModule) }
}