package com.halcyonmobile.multiplatformplayground.api

import io.ktor.client.engine.android.Android

internal actual val engine by lazy { Android.create() }
