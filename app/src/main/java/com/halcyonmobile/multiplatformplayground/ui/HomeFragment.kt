package com.halcyonmobile.multiplatformplayground.ui

import com.halcyonmobile.multiplatformplayground.HomeFragmentBinding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.AppPortfolioFragment
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel

class HomeFragment : AppPortfolioFragment<HomeFragmentBinding, HomeViewModel>(R.layout.fragment_home) {

    override val viewModel: HomeViewModel by viewModel()
}