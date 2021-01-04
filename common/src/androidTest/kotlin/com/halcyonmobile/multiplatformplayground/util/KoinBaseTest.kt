package com.halcyonmobile.multiplatformplayground.util

import android.content.Context
import com.halcyonmobile.multiplatformplayground.di.commonModules
import com.halcyonmobile.multiplatformplayground.di.platformModule
import io.mockk.mockk
import org.junit.Rule
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class KoinBaseTest : BaseTest(), KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(commonModules + platformModule + module {
            factory<Context> { mockk() }
        })
    }
}