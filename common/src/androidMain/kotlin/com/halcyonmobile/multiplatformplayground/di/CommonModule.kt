package com.halcyonmobile.multiplatformplayground.di

import android.content.Context
import com.halcyonmobile.multiplatformplayground.api.*
import com.halcyonmobile.multiplatformplayground.api.db.DatabaseFactory
import com.halcyonmobile.multiplatformplayground.repository.FavouritesRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.usecase.*
import org.koin.dsl.module

private fun getApiModule(context: Context) = module {
    single<KtorApi> { KtorApiImpl }

    factory { ApplicationApi(get()) }
    factory { CategoryApi(get()) }
    factory { ScreenshotApi(get()) }
    factory { FavouritesApi(get()) }

    single { DatabaseFactory.getInstance(context).create() }
}

private fun getRepositoryModule() = module {
    factory { ApplicationRemoteSource(get()) }
    single { ApplicationRepository(get()) }

    factory { CategoryLocalSource(get()) }
    factory { CategoryRemoteSource(get()) }
    single { CategoryRepository(get(), get()) }

    factory { FavouritesRemoteSource(get()) }
}

private fun getUseCaseModule() = module {
    factory { GetCategoriesUseCase(get()) }
    factory { GetCategoryUseCase(get()) }
    factory { FetchCategoriesUseCase(get()) }
    factory { GetFavouritesUseCase(get()) }
    factory { GetApplicationDetailUseCase(get()) }
    factory { GetApplicationsUseCase(get()) }
    factory { UpdateApplicationUseCase(get()) }
    factory { CreateApplicationUseCase(get()) }
}

fun getCommonModules(context: Context) =
    listOf(getApiModule(context), getRepositoryModule(), getUseCaseModule())