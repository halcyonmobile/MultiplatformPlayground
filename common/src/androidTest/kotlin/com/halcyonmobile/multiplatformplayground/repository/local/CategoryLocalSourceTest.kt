package com.halcyonmobile.multiplatformplayground.repository.local

import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.category
import com.halcyonmobile.multiplatformplayground.util.categoryEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CategoryLocalSourceTest : BaseTest() {

    private lateinit var sut: CategoryLocalSource

    @RelaxedMockK
    private lateinit var database: MultiplatformDatabase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = CategoryLocalSource(database)
    }

    @Test
    fun `When get categories returns categories Then the correctly mapped categories are returned`() =
        coroutineTestRule.runTest {
            val categories = listOf(categoryEntity)

            coEvery { database.categoriesQueries.selectAll().executeAsList() } returns categories
            val actual = sut.getCategories()
            val expected = listOf(category)

            assertEquals(expected, actual)
        }

    @Test
    fun `When get categories fails Then the exception is propagated downwards`() =
        coroutineTestRule.runTest {
            coEvery { database.categoriesQueries.selectAll() } throws RuntimeException()

            assertFails {
                sut.getCategories()
            }
        }
}