package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.halcyonmobile.multiplatformplayground.HomeBinding
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.util.applyEdgeToEdgeFlags

class AppPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseTheme_App)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<HomeBinding>(this, R.layout.activity_home)

        //TODO: Use nested Fragments instead of having the bottom navigation in the Activity for smoother transitions.
        val navController = findNavController(R.id.main_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.isVisible = destination.id in arrayOf(
                R.id.homeFragment,
                R.id.favouritesFragment,
                R.id.settingsFragment
            )
        }
    }

    override fun onResume() {
        super.onResume()
        applyEdgeToEdgeFlags()
    }
}