package com.halcyonmobile.multiplatformplayground.util

import org.junit.Rule

abstract class BaseTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
}