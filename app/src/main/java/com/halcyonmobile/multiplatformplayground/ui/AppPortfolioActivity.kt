package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import androidx.core.view.WindowCompat
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.ui.theme.MultiplatformPlaygroundTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class AppPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setTheme(R.style.BaseTheme_App)
        super.onCreate(savedInstanceState)
        setContent {
            MultiplatformPlaygroundTheme {
                ProvideWindowInsets {
                    Surface(color = MaterialTheme.colors.background) {
                        MainScreen()
                    }
                }
            }
        }
    }
}
