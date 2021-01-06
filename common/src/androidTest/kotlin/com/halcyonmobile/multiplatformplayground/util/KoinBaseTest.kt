package com.halcyonmobile.multiplatformplayground.util

import android.content.Context
import com.halcyonmobile.multiplatformplayground.di.getCommonModules
import com.halcyonmobile.multiplatformplayground.di.platformModule
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule
import org.koin.test.mock.MockProviderRule

abstract class KoinBaseTest : AutoCloseKoinTest() {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(getCommonModules(TestDispatcherProvider()) + platformModule + module {
            factory<Context> { mockk() }
        })
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }
}