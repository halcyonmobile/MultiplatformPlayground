package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.storage.LocalSourceImpl
import io.ktor.application.Application
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

internal fun getKodeinModule(application: Application) = Kodein {
    bind<LocalSource>() with singleton { LocalSourceImpl(application) }
}