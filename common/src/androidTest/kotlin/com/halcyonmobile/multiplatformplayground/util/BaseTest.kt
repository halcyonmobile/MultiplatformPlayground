package com.halcyonmobile.multiplatformplayground.util

import org.junit.Rule

abstract class BaseTest {

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()
}