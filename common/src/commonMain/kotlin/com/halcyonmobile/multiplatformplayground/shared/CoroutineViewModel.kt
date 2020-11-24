package com.halcyonmobile.multiplatformplayground.shared

import kotlinx.coroutines.CoroutineScope

/**
 * A base ViewModel abstraction over the Android Jetpack ViewModel implementation
 */
expect abstract class CoroutineViewModel() {

    val coroutineScope: CoroutineScope

    /**
     * Dispose all running operations under the coroutineScope
     */
    fun dispose()

    /**
     * (Same as the Android Arch onCleared)
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    protected open fun onCleared()
}