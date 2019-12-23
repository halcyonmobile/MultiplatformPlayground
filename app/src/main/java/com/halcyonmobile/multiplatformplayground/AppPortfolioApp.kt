package com.halcyonmobile.multiplatformplayground

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class AppPortfolioApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        // todo bindings
    }

    override fun onCreate() {
        super.onCreate()
        // todo setup Kodein
    }

}