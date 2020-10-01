package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.halcyonmobile.multiplatformplayground.HomeFragmentBinding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.AppPortfolioFragment
import com.halcyonmobile.multiplatformplayground.shared.util.log
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment :
    AppPortfolioFragment<HomeFragmentBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(requireBinding()) {
            textView.setText(this@HomeFragment.viewModel.title.stringRes.resourceId)
        }
        with(viewModel) {
            categories.onEach {
                log("Categories observed on Android: $it")
            }.launchIn(lifecycleScope)
            error.onEach {
                if (it != null) {
                    showSnackBar(it)
                }
            }.launchIn(lifecycleScope)
        }
    }
}