package com.halcyonmobile.multiplatformplayground.ui

import androidx.fragment.app.Fragment
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.SettingsViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class SettingsFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    val viewModel: SettingsViewModel by viewModel()
}