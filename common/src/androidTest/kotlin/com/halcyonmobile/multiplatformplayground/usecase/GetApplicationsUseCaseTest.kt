package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.applicationData
import com.halcyonmobile.multiplatformplayground.util.category
import com.halcyonmobile.multiplatformplayground.util.category2
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetApplicationsUseCaseTest : BaseTest() {

    private lateinit var sut: GetApplicationsUseCase

    @RelaxedMockK
    private lateinit var repository: ApplicationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        sut = GetApplicationsUseCase(repository)
    }

    @Test
    fun `When repository fails to retrieve applications Then correct error response is returned`() =
        coroutineTestRule.runTest {
            val exception = RuntimeException()
            coEvery { repository.getByCategory(any(), any(), any()) } throws exception

            val expected = Result.Error<List<Application>>(exception)
            assertEquals(expected, actual = sut(0, 0, 10))
        }

    @Test
    fun `When repository retrieves applications successfully Then the applications are returned`() =
        coroutineTestRule.runTest {
            val applications = listOf(applicationData)
            coEvery { repository.getByCategory(any(), any(), any()) } returns applications

            assertEquals(expected = Result.Success(applications), actual = sut(0, 0, 10))
        }
}