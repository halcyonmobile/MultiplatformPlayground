package com.halcyonmobile.multiplatformplayground.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class CoroutineViewModel : CoroutineScope {

    private val job = Job()

    // todo update this
    override val coroutineContext: CoroutineContext
        get() = Job()

    open fun onDestroy() {
        job.cancel()
    }
}