package com.nagyrobi.multiplatformplayground.api

import com.nagyrobi.multiplatformplayground.model.Application
import com.nagyrobi.multiplatformplayground.model.Screenshot
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
        parameter("page", page)
        parameter("perPage", perPage)
        parameter("categoryId", categoryId)
    }

    suspend fun getApplicationDetails(id: Long): Application = client.get {
        apiUrl("applications/$id")
    }

    // TODO implement this
//    suspend fun createApplication(application: Application, screenshotList: List<Screenshot>?): Unit = client.post{
//        body = MultiPartFormDataContent(formData {
//            append()
//        })
//
//    }
}