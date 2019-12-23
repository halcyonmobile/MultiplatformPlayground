package com.halcyonmobile.multiplatformplayground.ui

import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.SettingsFragmentBinding
import com.halcyonmobile.multiplatformplayground.shared.AppPortfolioFragment
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.SettingsViewModel

class SettingsFragment :
    AppPortfolioFragment<SettingsFragmentBinding, SettingsViewModel>(R.layout.fragment_settings) {

    override val viewModel: SettingsViewModel by viewModel()
}