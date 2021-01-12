package com.halcyonmobile.multiplatformplayground.viewmodel

import app.cash.turbine.test
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationsUseCase
import com.halcyonmobile.multiplatformplayground.util.KoinBaseTest
import com.halcyonmobile.multiplatformplayground.util.fullPageApplicationData
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test
import org.koin.test.mock.declareMock
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationsViewModelTest : KoinBaseTest() {

    private lateinit var sut: ApplicationsViewModel
    private lateinit var getApplications: GetApplicationsUseCase

    @Before
    fun setup() {
        getApplications = koinTestRule.koin.declareMock {
            coEvery { this@declareMock(any(), any(), any()) } returns Result.Success(
                fullPageApplicationData
            )
            sut = ApplicationsViewModel(1)
        }
    }

    @Test
    fun `When data is loaded Then state is Normal`() =
        coroutineTestRule.runTest {
            assertEquals(expected = ApplicationsViewModel.State.NORMAL, actual = sut.state.first())
        }

    @Test
    fun `When data is empty Then state is Empty`() =
        coroutineTestRule.runTest {
            coEvery { getApplications(any(), any(), any()) } returns Result.Success(emptyList())

            sut.refresh()
            assertEquals(expected = ApplicationsViewModel.State.EMPTY, actual = sut.state.first())
        }

    @Test
    fun `Given no data When data can't be retrieved Then state is Error`() =
        coroutineTestRule.runTest {
            coEvery {
                getApplications(
                    any(),
                    any(),
                    any()
                )
            } returns Result.Error(RuntimeException())

            sut.refresh()
            assertEquals(expected = ApplicationsViewModel.State.ERROR, actual = sut.state.first())
        }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `Given non empty data When more data can't be loaded Then event is sent out`() =
        coroutineTestRule.runTest {
            coEvery {
                getApplications(
                    any(),
                    any(),
                    any()
                )
            } returns Result.Error(RuntimeException())

            sut.event.test {
                sut.load()
                assertEquals(expected = ApplicationsViewModel.Event.ERROR, actual = expectItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
}