package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.category
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCategoryUseCaseTest : BaseTest() {

    private lateinit var sut: GetCategoryUseCase

    @RelaxedMockK
    private lateinit var repository: CategoryRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        sut = GetCategoryUseCase(repository)
    }

    @Test
    fun `When repository fails to retrieve category Then correct error response is returned`() =
        coroutineTestRule.runTest {
            coEvery { repository.get(any()) } returns null

            val actual = sut(0)
            assertTrue { (actual as? Result.Error)?.exception is IllegalArgumentException }
        }

    @Test
    fun `When repository retrieves category successfully Then the categories are returned`() =
        coroutineTestRule.runTest {
            coEvery { repository.get(any()) } returns category

            assertEquals(expected = Result.Success(category), actual = sut(0))
        }
}