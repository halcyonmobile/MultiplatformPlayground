package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.api.ScreenshotApi
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationLocalSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationMemorySource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.usecase.*
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

private val apiModule = Kodein.Module("apiModule") {
    bind<ApplicationApi>() with provider { ApplicationApi() }
    bind<CategoryApi>() with provider { CategoryApi() }
    bind<ScreenshotApi>() with provider { ScreenshotApi() }
}

private val repositoryModule = Kodein.Module("repositoryModule") {
    import(apiModule)

    bind<ApplicationLocalSource>() with provider { ApplicationMemorySource() }
    bind<ApplicationRemoteSource>() with provider { ApplicationRemoteSource(instance()) }
    bind<ApplicationRepository>() with singleton { ApplicationRepository(instance(), instance()) }

    bind<CategoryLocalSource>() with provider { CategoryLocalSource() }
    bind<CategoryRemoteSource>() with provider { CategoryRemoteSource(instance()) }
    bind<CategoryRepository>() with singleton { CategoryRepository(instance(), instance()) }
}

private val useCaseModule = Kodein.Module("useCaseModule") {
    import(repositoryModule)

    bind<GetCategoriesUseCase>() with provider { GetCategoriesUseCase(instance()) }
    bind<UploadScreenshotUseCase>() with provider { UploadScreenshotUseCase(instance()) }
    bind<GetFavouritesUseCase>() with provider { GetFavouritesUseCase(instance()) }
    bind<GetApplicationUseCase>() with provider { GetApplicationUseCase(instance()) }
    bind<GetApplicationsUseCase>() with provider { GetApplicationsUseCase(instance()) }
}

val kodein = Kodein {
    import(useCaseModule)
}