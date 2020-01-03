package com.halcyonmobile.multiplatformplayground.shared

import kotlinx.coroutines.*
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext

actual open class CoroutineViewModel {
    private val viewModelJob = SupervisorJob()
    actual val coroutineScope = CoroutineScope(IosMainDispatcher + viewModelJob)

    protected actual open fun onCleared() {
        viewModelJob.cancelChildren()
    }

    object IosMainDispatcher : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(dispatch_get_main_queue()) { block.run() }
        }
    }
}