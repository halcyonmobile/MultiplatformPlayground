package com.halcyonmobile.multiplatformplayground.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.halcyonmobile.multiplatformplayground.HomeBinding
import com.halcyonmobile.multiplatformplayground.R

class AppPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<HomeBinding>(this, R.layout.activity_home)
        val navController = findNavController(R.id.main_host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
        //TODO: Use nested Fragments instead of having the bottom navigation in the Activity for smoother transitions.
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
        window.run {
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_VISIBLE).let {
                if (Configuration.UI_MODE_NIGHT_MASK and resources.configuration.uiMode == Configuration.UI_MODE_NIGHT_YES) it else it or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}