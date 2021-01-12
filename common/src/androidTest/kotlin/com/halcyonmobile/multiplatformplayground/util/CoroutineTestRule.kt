package com.halcyonmobile.multiplatformplayground.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Coroutine Test Rule for overriding the [Dispatchers.Main] with [TestCoroutineDispatcher]
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    /**
     * Use this method for running suspend methods inside your tests
     */
    fun <T> runTest(block: suspend CoroutineScope.() -> T) = testDispatcher.runBlockingTest {
        block(this)
    }
}