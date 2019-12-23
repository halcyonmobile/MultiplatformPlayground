package com.halcyonmobile.multiplatformplayground.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
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
}