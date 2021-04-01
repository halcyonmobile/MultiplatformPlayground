package com.halcyonmobile.multiplatformplayground.viewmodel

import app.cash.turbine.test
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationDetailUseCase
import com.halcyonmobile.multiplatformplayground.usecase.UpdateApplicationUseCase
import com.halcyonmobile.multiplatformplayground.util.KoinBaseTest
import com.halcyonmobile.multiplatformplayground.util.applicationDetailData
import com.halcyonmobile.multiplatformplayground.util.applicationDetailUiModelData
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test
import org.koin.test.mock.declareMock
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

class ApplicationDetailViewModelTest : KoinBaseTest() {

    private lateinit var sut: ApplicationDetailViewModel

    private lateinit var getApplicationDetail: GetApplicationDetailUseCase

    private lateinit var updateApplication: UpdateApplicationUseCase

    @Before
    fun setup() {
        getApplicationDetail = koinTestRule.koin.declareMock {
            coEvery { this@declareMock(any()) } returns Result.Success(applicationDetailData)
        }
        updateApplication = koinTestRule.koin.declareMock {
            coEvery { this@declareMock(any()) } returns Result.Success(Unit)
        }
        sut = ApplicationDetailViewModel(0)
    }

    @Test
    fun `Given that application detail is retrieved Then state is correctly set`() =
        coroutineTestRule.runTest {
            coEvery { getApplicationDetail(any()) } returns Result.Success(applicationDetailData)
            assertEquals(
                expected = ApplicationDetailViewModel.State.NORMAL,
                actual = sut.state.first()
            )
            assertEquals(
                expected = applicationDetailUiModelData,
                actual = sut.applicationDetailUiModel.value
            )
        }

    @Test
    fun `Given that application detail can't be retrieved Then state is correctly set to error`() =
        coroutineTestRule.runTest {
            coEvery { getApplicationDetail(any()) } returns Result.Error(RuntimeException())

            sut.loadDetail()

            assertEquals(
                expected = ApplicationDetailViewModel.State.ERROR,
                actual = sut.state.first()
            )
        }

    @Test
    fun `Given application detail When application is marked as favourite successfully Then the state is also updated`() =
        coroutineTestRule.runTest {
            coEvery { getApplicationDetail(any()) } returns Result.Success(
                applicationDetailData.copy(
                    favourite = false
                )
            )
            coEvery { updateApplication(any()) } returns Result.Success(Unit)

            sut.loadDetail()
            sut.updateFavourite()

            assertEquals(
                expected = applicationDetailUiModelData.copy(favourite = true),
                actual = sut.applicationDetailUiModel.value
            )
        }

    @Test
    fun `Given application detail When application is unmarked as favourite successfully Then the state is also updated`() =
        coroutineTestRule.runTest {
            coEvery { getApplicationDetail(any()) } returns Result.Success(
                applicationDetailData.copy(
                    favourite = true
                )
            )
            coEvery { updateApplication(any()) } returns Result.Success(Unit)

            sut.updateFavourite()

            assertEquals(
                expected = applicationDetailUiModelData.copy(favourite = false),
                actual = sut.applicationDetailUiModel.value
            )
        }

    @OptIn(ExperimentalTime::class, ExperimentalCoroutinesApi::class)
    @Test
    fun `Given application detail When favourite state is not updated Then correct event is sent out`() =
        coroutineTestRule.runTest {
            coEvery { updateApplication(any()) } returns Result.Error(RuntimeException())

            sut.event.test {
                sut.updateFavourite()
                assertEquals(expected = ApplicationDetailViewModel.Event.ERROR, expectItem())
                cancelAndIgnoreRemainingEvents()
            }

            assertEquals(
                expected = applicationDetailUiModelData,
                actual = sut.applicationDetailUiModel.value
            )
        }
}