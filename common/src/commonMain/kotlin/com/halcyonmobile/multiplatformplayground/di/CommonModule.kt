package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.api.*
import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.halcyonmobile.multiplatformplayground.repository.FavouritesRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.util.DefaultDispatcherProvider
import com.halcyonmobile.multiplatformplayground.shared.util.DispatcherProvider
import com.halcyonmobile.multiplatformplayground.usecase.*
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private fun getApiModule(dispatcherProvider: DispatcherProvider) = module {
    factory { dispatcherProvider }
    single<KtorApi> { KtorApiImpl }

    factory { ApplicationApi(get()) }
    factory { CategoryApi(get()) }
    factory { ScreenshotApi(get()) }
    factory { FavouritesApi(get()) }

    single { MultiplatformDatabase(get()) }
}

private val repositoryModule = module {
    factory { ApplicationRemoteSource(get(), get()) }
    single { ApplicationRepository(get()) }

    factory { CategoryLocalSource(get()) }
    factory { CategoryRemoteSource(get(), get()) }
    single { CategoryRepository(get(), get()) }

    factory { FavouritesRemoteSource(get(), get()) }
}

private val useCaseModule = module {
    factory { GetCategoriesUseCase(get()) }
    factory { GetCategoryUseCase(get()) }
    factory { FetchCategoriesUseCase(get()) }
    factory { GetFavouritesUseCase(get()) }
    factory { GetApplicationDetailUseCase(get()) }
    factory { GetApplicationsUseCase(get()) }
    factory { UpdateApplicationUseCase(get()) }
    factory { CreateApplicationUseCase(get()) }
}

internal fun getCommonModules(dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()) =
    listOf(getApiModule(dispatcherProvider), repositoryModule, useCaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(getCommonModules() + platformModule)
}

fun initKoin() = initKoin {}
