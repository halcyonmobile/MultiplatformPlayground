package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.model.ApplicationDetailResponse
import com.halcyonmobile.multiplatformplayground.shared.util.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*

internal class ApplicationApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {

    suspend fun getApplicationsByCategory(
        page: Int,
        perPage: Int,
        categoryId: Long
    ): List<Application> = client.get {
        apiUrl(APPLICATIONS_BASE_URL)
        parameter(PAGE, page)
        parameter(PER_PAGE, perPage)
        parameter(CATEGORY_ID_QUERY, categoryId)
    }

    suspend fun getApplicationDetail(id: Long): ApplicationDetailResponse = client.get {
        apiUrl("applications/$id")
    }

    suspend fun createApplication(applicationRequest: ApplicationRequest): Long = client.post {
        json()
        apiUrl(APPLICATIONS_BASE_URL)
        body = applicationRequest
    }

    suspend fun postIcon(appId: Long, icon: ImageFile): Unit = client.submitFormWithBinaryData(
        formData {
            appendInput(key = ICON_FILE_PART, headers = Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=${appId}_ic")
            }) {
                buildPacket { writeFully(icon.toByteArray()) }
            }
        }) {
        apiUrl("$APPLICATIONS_BASE_URL/${appId}/icon")
    }

    suspend fun postScreenshot(appId: Long, name: String, screenshot: ImageFile): Unit =
        client.submitFormWithBinaryData(
            formData {
                appendInput(key = SCREENSHOT_FILE_PART, headers = Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=$name")
                }) {
                    buildPacket { writeFully(screenshot.toByteArray()) }
                }
                append(FormPart(SCREENSHOT_NAME_PART, name))
            }) {
            apiUrl("$APPLICATIONS_BASE_URL/${appId}/screenshot")
        }

    suspend fun update(application: Application): Unit = client.put {
        json()
        apiUrl(APPLICATIONS_BASE_URL)
        body = application
    }

    companion object {
        const val APPLICATIONS_BASE_URL = "applications"
    }
}
