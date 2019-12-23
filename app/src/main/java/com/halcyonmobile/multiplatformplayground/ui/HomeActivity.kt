package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.halcyonmobile.multiplatformplayground.HomeBinding
import com.halcyonmobile.multiplatformplayground.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<HomeBinding>(this, R.layout.activity_home)

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
}