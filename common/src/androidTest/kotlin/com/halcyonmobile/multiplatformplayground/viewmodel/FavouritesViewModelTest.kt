package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetFavouritesUseCase
import com.halcyonmobile.multiplatformplayground.util.KoinBaseTest
import com.halcyonmobile.multiplatformplayground.util.applicationData
import com.halcyonmobile.multiplatformplayground.util.applicationUiModelData
import com.halcyonmobile.multiplatformplayground.util.fullPageApplicationData
import io.mockk.coEvery
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test
import org.koin.test.mock.declareMock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FavouritesViewModelTest : KoinBaseTest() {

    private lateinit var sut: FavouritesViewModel
    private lateinit var getFavourites: GetFavouritesUseCase

    @Before
    fun setup() {
        getFavourites = koinTestRule.koin.declareMock {
            coEvery { this@declareMock() } returns Result.Success(fullPageApplicationData)
        }

        sut = FavouritesViewModel()
    }

    @Test
    fun `When applications are retrieved Then the state is Normal and items are updated`() =
        coroutineTestRule.runTest {
            coEvery { getFavourites() } returns Result.Success(listOf(applicationData))

            sut.loadFavourites()
            assertEquals(expected = listOf(applicationUiModelData), sut.favourites.first())
            assertEquals(expected = FavouritesViewModel.State.NORMAL, actual = sut.state.first())
        }

    @Test
    fun `When applications are empty Then the state is Error`() =
        coroutineTestRule.runTest {
            coEvery { getFavourites() } returns Result.Error(RuntimeException())

            sut.loadFavourites()
            assertEquals(expected = FavouritesViewModel.State.ERROR, actual = sut.state.first())
        }
}