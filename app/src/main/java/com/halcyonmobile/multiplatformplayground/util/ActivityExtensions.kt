package com.halcyonmobile.multiplatformplayground.util

import android.app.Activity
import android.view.View

private const val EDGE_TO_EDGE_FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_VISIBLE

fun Activity.applyEdgeToEdgeFlags() {
    window.decorView.systemUiVisibility = if (isInNightMode) EDGE_TO_EDGE_FLAGS else EDGE_TO_EDGE_FLAGS or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}