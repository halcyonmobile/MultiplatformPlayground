package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.category
import com.halcyonmobile.multiplatformplayground.util.category2
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetCategoriesUseCaseTest : BaseTest() {

    private lateinit var sut: GetCategoriesUseCase

    @RelaxedMockK
    private lateinit var repository: CategoryRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        sut = GetCategoriesUseCase(repository)
    }

    @Test
    fun `When repository fails to retrieve categories Then correct error response is returned`() =
        coroutineTestRule.runTest {
            val exception = RuntimeException()
            coEvery { repository.get() } throws exception

            val expected = Result.Error<List<Category>>(exception)
            assertEquals(expected, actual = sut())
        }

    @Test
    fun `When repository retrieves categories successfully Then the categories are returned`() =
        coroutineTestRule.runTest {
            val categories = listOf(category, category2)
            coEvery { repository.get() } returns categories

            assertEquals(expected = Result.Success(categories), actual = sut())
        }
}