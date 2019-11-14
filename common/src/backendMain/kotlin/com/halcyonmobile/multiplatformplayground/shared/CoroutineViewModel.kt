package com.halcyonmobile.multiplatformplayground.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual open class CoroutineViewModel actual constructor() {
    actual val coroutineScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main)

}