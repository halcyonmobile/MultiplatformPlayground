package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.repository.FavouritesRemoteSource
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.applicationData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetFavouritesUseCaseTest : BaseTest() {

    private lateinit var sut: GetFavouritesUseCase

    @RelaxedMockK
    private lateinit var remoteSource: FavouritesRemoteSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        sut = GetFavouritesUseCase(remoteSource)
    }

    @Test
    fun `When repository fails to retrieve favourites Then correct error response is returned`() =
        coroutineTestRule.runTest {
            val exception = RuntimeException()
            coEvery { remoteSource.get() } throws exception

            val expected = Result.Error<List<Application>>(exception)
            assertEquals(expected, actual = sut())
        }

    @Test
    fun `When repository retrieves favourites successfully Then the favourites are returned`() =
        coroutineTestRule.runTest {
            val applications = listOf(applicationData)
            coEvery { remoteSource.get() } returns applications

            assertEquals(expected = Result.Success(applications), actual = sut())
        }

}