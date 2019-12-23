package com.halcyonmobile.multiplatformplayground.ui

import com.halcyonmobile.multiplatformplayground.HomeFragmentBinding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.AppPortfolioFragment
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import org.kodein.di.KodeinAware

class HomeFragment :
    AppPortfolioFragment<HomeFragmentBinding, HomeViewModel>(R.layout.fragment_home), KodeinAware {

    override val viewModel: HomeViewModel by viewModel()
}