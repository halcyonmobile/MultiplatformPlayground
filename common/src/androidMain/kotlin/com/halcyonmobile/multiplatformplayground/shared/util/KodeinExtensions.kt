package com.halcyonmobile.multiplatformplayground.shared.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein.Builder
import org.kodein.di.KodeinAware
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

inline fun <reified T : ViewModel> Builder.bindViewModel(overrides: Boolean? = null): Builder.TypeBinder<T> {
    return bind<T>(T::class.java.simpleName, overrides)
}

inline fun <reified VM : ViewModel, T> T.viewModel(): Lazy<VM> where T : KodeinAware, T : AppCompatActivity {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}