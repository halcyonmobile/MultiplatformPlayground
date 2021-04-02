package com.halcyonmobile.multiplatformplayground.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.halcyonmobile.multiplatformplayground.R
import com.halcyonmobile.multiplatformplayground.ui.theme.MultiplatformPlaygroundTheme

class AppPortfolioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setTheme(R.style.BaseTheme_App)
        super.onCreate(savedInstanceState)
        setContent {
            MultiplatformPlaygroundTheme {
                ProvideWindowInsets {
                    Surface {
                        MainScreen()
                    }
                }
            }
        }
    }
}
