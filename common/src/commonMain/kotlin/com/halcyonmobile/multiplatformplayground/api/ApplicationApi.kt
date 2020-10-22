package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.model.ApplicationDetailResponse
import com.halcyonmobile.multiplatformplayground.shared.util.*
import io.ktor.client.request.*

internal class ApplicationApi : KtorApi() {

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

    suspend fun createApplication(
        applicationRequest: ApplicationRequest
    ): Unit = client.post {
        json()
        apiUrl(APPLICATIONS_BASE_URL)
        body = applicationRequest
    }

    suspend fun update(application: Application): Unit = client.put {
        json()
        apiUrl(APPLICATIONS_BASE_URL)
        body = application
    }

    companion object {
        private const val APPLICATIONS_BASE_URL = "applications"
    }
}
