package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.ApplicationDetail
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.applicationDetailData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetApplicationDetailUseCaseTest : BaseTest() {

    private lateinit var sut: GetApplicationDetailUseCase

    @RelaxedMockK
    private lateinit var repository: ApplicationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        sut = GetApplicationDetailUseCase(repository)
    }

    @Test
    fun `When repository fails to retrieve application detail Then correct error response is returned`() =
        coroutineTestRule.runTest {
            val exception = RuntimeException()
            coEvery { repository.getDetailById(any()) } throws exception

            val expected = Result.Error<ApplicationDetail>(exception)
            assertEquals(expected, actual = sut(0))
        }

    @Test
    fun `When repository retrieves application detail successfully Then the application detail is returned`() =
        coroutineTestRule.runTest {
            coEvery { repository.getDetailById(any()) } returns applicationDetailData

            assertEquals(expected = Result.Success(applicationDetailData), actual = sut(1))
        }
}