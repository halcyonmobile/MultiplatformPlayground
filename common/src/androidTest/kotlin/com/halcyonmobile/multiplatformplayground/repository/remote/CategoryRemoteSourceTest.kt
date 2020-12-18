package com.halcyonmobile.multiplatformplayground.repository.remote

import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.TestDispatcherProvider
import com.halcyonmobile.multiplatformplayground.util.category
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CategoryRemoteSourceTest : BaseTest() {

    private lateinit var sut: CategoryRemoteSource

    @RelaxedMockK
    private lateinit var api: CategoryApi

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = CategoryRemoteSource(api, TestDispatcherProvider())
    }

    @Test
    fun `When get categories returns list of categories Then the mapped categories list is returned`() =
        coroutineTestRule.runTest {
            val categories = listOf(category)

            coEvery { api.getCategories(any(), any()) } returns categories
            val actual = sut.get(0, 10)

            assertEquals(categories, actual)
        }

    @Test
    fun `When get categories fails Then the failure is propagated downward`() =
        coroutineTestRule.runTest {
            coEvery { api.getCategories(any(), any()) } throws RuntimeException()

            assertFails { sut.get(0, 10) }
        }
}