package com.halcyonmobile.multiplatformplayground.shared.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
// TODO replace this workaround, when proper solution is released
fun <T> StateFlow<T>.onEachHelper(callback: (T) -> Unit) {
    onEach {
        callback(it)
    }.launchIn(CoroutineScope(Dispatchers.Main))
}