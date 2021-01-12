package com.halcyonmobile.multiplatformplayground

import com.halcyonmobile.multiplatformplayground.util.KoinBaseTest
import org.junit.experimental.categories.Category
import org.koin.test.category.CheckModuleTest
import org.koin.test.check.checkModules
import kotlin.test.Test

@Category(CheckModuleTest::class)
class KoinModuleTest : KoinBaseTest() {

    @Test
    fun checkCommonModules() = koinTestRule.koin.checkModules()
}