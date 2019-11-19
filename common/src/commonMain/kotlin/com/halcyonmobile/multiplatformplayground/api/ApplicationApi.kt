package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.halcyonmobile.multiplatformplayground.shared.util.*
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class ApplicationApi : KtorApi() {

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

    suspend fun getApplicationDetails(id: Long): Application = client.get {
        apiUrl("applications/$id")
    }

    suspend fun createApplication(
        application: Application,
        screenshotList: List<Screenshot>?
    ): Unit = client.post {
        body = MultiPartFormDataContent(formData {
            append(APP_NAME, application.name)
            append(APP_DEVELOPER, application.developer)
            application.description?.let { append(APP_DESCRIPTION, it) }
            application.category?.id?.let { append(APP_CATEGORY_ID, it) }
            application.rating?.let { append(APP_RATING, it) }
            application.downloads?.let { append(APP_DOWNLOADS, it) }
        }
            // TODO complete this

        )
    }
}
