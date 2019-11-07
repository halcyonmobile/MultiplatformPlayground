package com.halcyonmobile.multiplatformplayground.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

actual open class CoroutineViewModel : ViewModel() {

    actual val coroutineScope = viewModelScope

}