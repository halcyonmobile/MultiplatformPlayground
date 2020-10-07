package com.halcyonmobile.multiplatformplayground.ui

import androidx.fragment.app.Fragment
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class HomeFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    val viewModel: HomeViewModel by viewModel()


}