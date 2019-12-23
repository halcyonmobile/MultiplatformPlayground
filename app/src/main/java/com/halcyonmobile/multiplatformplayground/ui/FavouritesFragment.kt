package com.halcyonmobile.multiplatformplayground.ui

import com.halcyonmobile.multiplatformplayground.FavouritesFragmentBinding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.shared.AppPortfolioFragment
import com.halcyonmobile.multiplatformplayground.viewmodel.FavouritesViewModel

class FavouritesFragment : AppPortfolioFragment<FavouritesFragmentBinding, FavouritesViewModel>(R.layout.fragment_favourites) {

    override val viewModel: FavouritesViewModel
}