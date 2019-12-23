package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.model.ApplicationDetailResponse
import com.halcyonmobile.multiplatformplayground.shared.util.*
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

internal class ApplicationApi : KtorApi() {

    suspend fun getApplicationsByCategory(
        page: Int,
        perPage: Int,
        categoryId: Long
    ): List<Application> = client.get {
        apiUrl("applications/filter")
        parameter(PAGE, page)
        parameter(PER_PAGE, perPage)
        parameter(APP_CATEGORY_ID, categoryId)
    }

    suspend fun getApplicationDetails(id: Long): ApplicationDetailResponse = client.get {
        apiUrl("applications/$id")
    }

    suspend fun createApplication(
        applicationRequest: ApplicationRequest
    ) = client.post<Unit> {
        apiUrl("applications")
        body = applicationRequest
    }
}
