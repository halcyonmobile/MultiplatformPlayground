package com.halcyonmobile.multiplatformplayground.ui

import androidx.fragment.app.Fragment
import com.halcyonmobile.multiplatformplayground.viewmodel.FavouritesViewModel
import com.halcyonmobile.multiplatformplayground.shared.util.viewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

class FavouritesFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    val viewModel: FavouritesViewModel by viewModel()
}