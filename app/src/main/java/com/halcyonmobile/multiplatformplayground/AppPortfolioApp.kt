package com.halcyonmobile.multiplatformplayground

import android.app.Application
import com.halcyonmobile.multiplatformplayground.di.bindCommonModule
import com.halcyonmobile.multiplatformplayground.di.bindViewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class AppPortfolioApp : Application(), KodeinAware {

    override val kodein = Kodein {
        bindCommonModule()
        bindViewModelModule()
    }
}