package com.nagyrobi.multiplatformplayground.di

import com.nagyrobi.multiplatformplayground.api.ApplicationApi
import com.nagyrobi.multiplatformplayground.api.CategoryApi
import com.nagyrobi.multiplatformplayground.api.ScreenshotApi
import com.nagyrobi.multiplatformplayground.repository.application.ApplicationLocalSource
import com.nagyrobi.multiplatformplayground.repository.application.ApplicationMemorySource
import com.nagyrobi.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.nagyrobi.multiplatformplayground.repository.application.ApplicationRepository
import com.nagyrobi.multiplatformplayground.repository.category.CategoryLocalSource
import com.nagyrobi.multiplatformplayground.repository.category.CategoryRemoteSource
import com.nagyrobi.multiplatformplayground.repository.category.CategoryRepository
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

val kodein = Kodein {
    import(repositoryModule)
}