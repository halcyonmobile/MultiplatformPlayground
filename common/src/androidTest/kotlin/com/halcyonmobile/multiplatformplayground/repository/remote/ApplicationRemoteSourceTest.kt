package com.halcyonmobile.multiplatformplayground.repository.remote

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.ApplicationRequest
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import com.halcyonmobile.multiplatformplayground.shared.util.toByteArray
import com.halcyonmobile.multiplatformplayground.util.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ApplicationRemoteSourceTest : BaseTest() {

    private lateinit var sut: ApplicationRemoteSource

    @RelaxedMockK
    private lateinit var api: ApplicationApi

    @RelaxedMockK
    private lateinit var imageFile: ImageFile

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = ApplicationRemoteSource(api, TestDispatcherProvider())

        val dummyByteArray = (1..10).map { it.toByte() }.toByteArray()
        coEvery { imageFile.toByteArray() } returns dummyByteArray
    }

    @Test
    fun `When get applications returns a list of applications Then the mapped list of applications is returned`() =
        coroutineTestRule.runTest {
            val applicationsResponse = listOf(applicationData)
            coEvery {
                api.getApplicationsByCategory(
                    any(),
                    any(),
                    any()
                )
            } returns applicationsResponse

            val actual = sut.get(applicationData.id, 0, 10)

            assertEquals(applicationsResponse, actual)
        }

    @Test
    fun `When get applications fails Then the exception is propagated downward`() =
        coroutineTestRule.runTest {
            coEvery { api.getApplicationsByCategory(any(), any(), any()) } throws RuntimeException()

            assertFails { sut.get(0, 10, 0) }
        }

    @Test
    fun `When get application detail returns application detail Then the mapped response is returned`() =
        coroutineTestRule.runTest {
            val applicationDetailResponse = applicationDetailResponseData

            coEvery { api.getApplicationDetail(any()) } returns applicationDetailResponse

            val actual = sut.getDetail(applicationDetailResponse.id)

            assertEquals(applicationDetailData, actual)
        }

    @Test
    fun `When get application detail fails Then the exception is propagated downward`() =
        coroutineTestRule.runTest {
            coEvery { api.getApplicationsByCategory(any(), any(), any()) } throws RuntimeException()

            assertFails { sut.getDetail(0) }
        }

    @Test
    fun `When create application is called Then the correct request is sent`() =
        coroutineTestRule.runTest {
            val applicationUpload = getUploadApplication(imageFile, listOf(imageFile))
            val expected = getUploadApplicationRequest(imageFile, listOf(imageFile))

            val actualSlot = slot<ApplicationRequest>()
            coEvery { api.createApplication(capture(actualSlot)) }
            sut.create(applicationUpload)

            assertEquals(expected, actualSlot.captured)
        }

    @Test
    fun `When create application fails Then the exception is propagated downward`() =
        coroutineTestRule.runTest {
            coEvery { api.createApplication(any()) } throws RuntimeException()

            assertFails { sut.create(getUploadApplication(imageFile, listOf(imageFile))) }
        }

    @Test
    fun `When update application is called Then the correct request is sent`() =
        coroutineTestRule.runTest {
            val actualSlot = slot<Application>()

            coEvery { api.update(capture(actualSlot)) } returns Unit
            sut.update(applicationData)

            assertEquals(applicationData, actualSlot.captured)
        }

    @Test
    fun `When update application fails Then the exception is propagated downward`() =
        coroutineTestRule.runTest {
            coEvery { api.update(any()) } throws RuntimeException()

            assertFails { sut.update(applicationData) }
        }
}