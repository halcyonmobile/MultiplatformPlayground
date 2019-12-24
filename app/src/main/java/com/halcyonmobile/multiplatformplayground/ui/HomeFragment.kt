package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.halcyonmobile.multiplatformplayground.HomeFragmentBinding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.AppPortfolioFragment
import com.halcyonmobile.multiplatformplayground.shared.observer.LiveDataObserver
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import org.kodein.di.KodeinAware

class HomeFragment :
    AppPortfolioFragment<HomeFragmentBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.error.observe(viewLifecycleOwner, Observer {
            showSnackBar(it)
        })
    }
}