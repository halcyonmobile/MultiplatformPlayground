package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.ui.theme.MultiplatformPlaygroundTheme

class AppPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BaseTheme_App)
        super.onCreate(savedInstanceState)
        setContent {
            MultiplatformPlaygroundTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        applyEdgeToEdgeFlags()
    }
}
