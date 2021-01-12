package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.api.FavouritesApi
import com.halcyonmobile.multiplatformplayground.api.KtorApi
import com.halcyonmobile.multiplatformplayground.api.KtorApiImpl
import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.halcyonmobile.multiplatformplayground.repository.FavouritesRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.util.DefaultDispatcherProvider
import com.halcyonmobile.multiplatformplayground.shared.util.DispatcherProvider
import com.halcyonmobile.multiplatformplayground.usecase.CreateApplicationUseCase
import com.halcyonmobile.multiplatformplayground.usecase.FetchCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationDetailUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationsUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoryUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetFavouritesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.UpdateApplicationUseCase
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private fun getApiModule(dispatcherProvider: DispatcherProvider) = module {
    factory { dispatcherProvider }
    single<KtorApi> { KtorApiImpl }

    factory { ApplicationApi(get()) }
    factory { CategoryApi(get()) }
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

// Used by iOS
fun initKoin() = initKoin {}
