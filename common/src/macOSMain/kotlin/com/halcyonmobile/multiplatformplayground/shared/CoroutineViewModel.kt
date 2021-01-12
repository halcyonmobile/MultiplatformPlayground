package com.halcyonmobile.multiplatformplayground.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual abstract class CoroutineViewModel {
    actual val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /**
     * Dispose all running operations under the coroutineScope
     */
    actual fun dispose() {
        coroutineScope.cancel()
        onCleared()
    }

    /**
     * (Same as the Android Arch onCleared)
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     * <p>
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    protected actual open fun onCleared() {
    }
}