package com.halcyonmobile.multiplatformplayground.di

import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider

@ThreadLocal
object CommonInjector {
    val kodein = Kodein.lazy {
        bindCommonModule()
        import(viewModelModule)
    }

    fun homeViewModel() = kodein.direct.instance<HomeViewModel>()
}

private val viewModelModule = Kodein.Module("viewModelModule") {
    bind<HomeViewModel>() with provider { HomeViewModel(instance(), instance(), instance()) }
}

