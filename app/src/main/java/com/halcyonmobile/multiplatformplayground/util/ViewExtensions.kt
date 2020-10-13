package com.halcyonmobile.multiplatformplayground.util

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.halcyonmobile.multiplatformplayground.MR

fun View.showSnackBar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, duration).show()
}

inline fun View.showSnackBar(@StringRes textRes: Int, crossinline action: () -> Unit) {
    Snackbar.make(this, context.getString(textRes), Snackbar.LENGTH_LONG)
        .setAction(MR.strings.retry.resourceId) { action() }.show()
}