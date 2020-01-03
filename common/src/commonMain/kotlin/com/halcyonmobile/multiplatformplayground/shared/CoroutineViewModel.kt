package com.halcyonmobile.multiplatformplayground.shared

import kotlinx.coroutines.CoroutineScope

expect open class CoroutineViewModel() {

    val coroutineScope: CoroutineScope
    protected open fun onCleared()
}