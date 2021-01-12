package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.getUploadApplication
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CreateApplicationUseCaseTest : BaseTest() {

    private lateinit var sut: CreateApplicationUseCase

    @RelaxedMockK
    private lateinit var repository: ApplicationRepository

    @RelaxedMockK
    private lateinit var imageFile: ImageFile

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = CreateApplicationUseCase(repository)
    }

    @Test
    fun `When repository fails to create application Then correct error response is returned`() =
        coroutineTestRule.runTest {
            val exception = RuntimeException()
            coEvery { repository.create(any()) } throws exception

            val uploadApplication = getUploadApplication(imageFile, listOf(imageFile))
            val expected = Result.Error<Unit>(exception)

            assertEquals(expected, actual = sut(uploadApplication))
        }

    @Test
    fun `When repository creates application successfully Then correct success response is returned`() =
        coroutineTestRule.runTest {
            coEvery { repository.create(any()) } returns Unit

            val uploadApplication = getUploadApplication(imageFile, listOf(imageFile))
            val expected = Result.Success(Unit)

            assertEquals(expected, actual = sut(uploadApplication))
        }
}