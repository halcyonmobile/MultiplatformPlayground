package com.halcyonmobile.multiplatformplayground.repository.remote

import com.halcyonmobile.multiplatformplayground.api.FavouritesApi
import com.halcyonmobile.multiplatformplayground.repository.FavouritesRemoteSource
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.TestDispatcherProvider
import com.halcyonmobile.multiplatformplayground.util.applicationData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class FavouritesRemoteSourceTest : BaseTest() {

    private lateinit var sut: FavouritesRemoteSource

    @RelaxedMockK
    private lateinit var api: FavouritesApi

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = FavouritesRemoteSource(api, TestDispatcherProvider())
    }

    @Test
    fun `When get favourites returns list of favourites Then the mapped favourites list is returned`() =
        coroutineTestRule.runTest {
            val favourites = listOf(applicationData)

            coEvery { api.getFavourites() } returns favourites
            val actual = sut.get()

            assertEquals(favourites, actual)
        }

    @Test
    fun `When get favourites fails Then the failure is propagated downward`() =
        coroutineTestRule.runTest {
            coEvery { api.getFavourites() } throws RuntimeException()

            assertFails { sut.get() }
        }
}