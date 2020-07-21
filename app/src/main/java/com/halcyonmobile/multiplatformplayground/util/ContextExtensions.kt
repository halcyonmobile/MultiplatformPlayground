package com.halcyonmobile.multiplatformplayground.util

import android.content.Context
import android.content.res.Configuration

val Context.isInNightMode get() = Configuration.UI_MODE_NIGHT_MASK and resources.configuration.uiMode == Configuration.UI_MODE_NIGHT_YES