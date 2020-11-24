package com.halcyonmobile.multiplatformplayground.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halcyonmobile.multiplatformplayground.shared.util.log
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

actual abstract class CoroutineViewModel : ViewModel() {

    actual val coroutineScope = viewModelScope

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
    actual override fun onCleared() {
        super.onCleared()
    }

}