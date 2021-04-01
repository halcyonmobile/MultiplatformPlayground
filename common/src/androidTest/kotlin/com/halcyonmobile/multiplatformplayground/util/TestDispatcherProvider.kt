package com.halcyonmobile.multiplatformplayground.util

import com.halcyonmobile.multiplatformplayground.shared.util.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProvider : DispatcherProvider {
    override val main = TestCoroutineDispatcher()
    override val io = TestCoroutineDispatcher()
    override val unconfined = TestCoroutineDispatcher()
}