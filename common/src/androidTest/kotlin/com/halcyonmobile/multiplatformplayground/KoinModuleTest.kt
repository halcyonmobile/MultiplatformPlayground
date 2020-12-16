package com.halcyonmobile.multiplatformplayground

import android.content.Context
import com.halcyonmobile.multiplatformplayground.di.initKoin
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.experimental.categories.Category
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import kotlin.test.Test

@Category(CheckModuleTest::class)
class KoinModuleTest : BaseTest(), KoinTest {

    @RelaxedMockK
    private lateinit var context: Context

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun checkCommonModules() = initKoin {
        loadKoinModules(
            module { factory { context } }
        )
    }.checkModules()
}