package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.storage.LocalSourceImpl
import io.ktor.application.Application
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.kodein.di.ktor.kodein

internal fun Application.installKodeinFeature() = kodein {
    bind<LocalSource>() with singleton { LocalSourceImpl(this@installKodeinFeature) }
}