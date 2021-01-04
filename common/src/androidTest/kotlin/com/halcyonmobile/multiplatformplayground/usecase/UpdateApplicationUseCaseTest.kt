package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.applicationData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UpdateApplicationUseCaseTest : BaseTest() {

    private lateinit var sut: UpdateApplicationUseCase

    @RelaxedMockK
    private lateinit var repository: ApplicationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = UpdateApplicationUseCase(repository)
    }

    @Test
    fun `When repository fails to update application Then correct error response is returned`() =
        coroutineTestRule.runTest {
            val exception = RuntimeException()
            coEvery { repository.update(any()) } throws exception

            val expected = Result.Error<Unit>(exception)

            assertEquals(expected, actual = sut(applicationData))
        }

    @Test
    fun `When repository creates application successfully Then correct success response is returned`() =
        coroutineTestRule.runTest {
            coEvery { repository.create(any()) } returns Unit

            val expected = Result.Success(Unit)

            assertEquals(expected, actual = sut(applicationData))
        }
}