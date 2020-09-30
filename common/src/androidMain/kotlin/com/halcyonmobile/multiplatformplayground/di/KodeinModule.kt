package com.halcyonmobile.multiplatformplayground.di

import android.content.Context
import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.api.ScreenshotApi
import com.halcyonmobile.multiplatformplayground.api.db.DatabaseFactory
import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationLocalSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationMemorySource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.usecase.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

private fun getApiModule(context: Context) = Kodein.Module("apiModule") {
    bind<ApplicationApi>() with provider { ApplicationApi() }
    bind<CategoryApi>() with provider { CategoryApi() }
    bind<ScreenshotApi>() with provider { ScreenshotApi() }

    bind<MultiplatformDatabase>() with singleton { DatabaseFactory.getInstance(context).create() }
}

private fun getRepositoryModule(context: Context) = Kodein.Module("repositoryModule") {
    import(getApiModule(context))

    bind<ApplicationLocalSource>() with provider { ApplicationMemorySource() }
    bind<ApplicationRemoteSource>() with provider { ApplicationRemoteSource(instance()) }
    bind<ApplicationRepository>() with singleton { ApplicationRepository(instance(), instance()) }

    bind<CategoryLocalSource>() with provider { CategoryLocalSource(instance()) }
    bind<CategoryRemoteSource>() with provider { CategoryRemoteSource(instance()) }
    bind<CategoryRepository>() with singleton { CategoryRepository(instance(), instance()) }
}

private fun getUseCaseModule(context: Context) = Kodein.Module("useCaseModule") {
    import(getRepositoryModule(context))

    bind<GetCategoriesUseCase>() with provider { GetCategoriesUseCase(instance()) }
    bind<FetchCategoriesUseCase>() with provider { FetchCategoriesUseCase(instance()) }
    bind<GetFavouritesUseCase>() with provider { GetFavouritesUseCase(instance()) }
    bind<GetApplicationUseCase>() with provider { GetApplicationUseCase(instance()) }
    bind<GetApplicationsUseCase>() with provider { GetApplicationsUseCase(instance()) }
    bind<UpdateFavouriteUseCase>() with provider { UpdateFavouriteUseCase(instance()) }
}

fun Kodein.MainBuilder.bindCommonModule(context: Context) {
    import(getUseCaseModule(context))
}