package com.halcyonmobile.multiplatformplayground.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.halcyonmobile.multiplatformplayground.BR
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

abstract class AppPortfolioFragment<VB : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutResourceId: Int) :
    Fragment(), KodeinAware {

    private var binding: VB? = null
    protected abstract val viewModel: VM

    override val kodein: Kodein by closestKodein()

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<VB>(inflater, layoutResourceId, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.setVariable(BR.viewModel, viewModel)
            binding = it
        }.root

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    protected fun requireBinding(): VB =
        binding ?: throw NullPointerException("View is in destroyed state and the Binding is null")

    protected fun showSnackBar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(requireBinding().root, text, duration).show()
    }

    protected inline fun showSnackBar(@StringRes textRes: Int, crossinline action: () -> Unit) {
        Snackbar.make(requireBinding().root, getString(textRes), Snackbar.LENGTH_LONG).setAction("retry") { action() }.show()
    }
}