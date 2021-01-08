package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.shared.util.toByteArray
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ApplicationRemoteSource internal constructor(private val applicationApi: ApplicationApi) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        withContext(Dispatchers.Default) {
            applicationApi.getApplicationsByCategory(offset, perPage, categoryId)
        }

    suspend fun create(uploadApplicationModel: UploadApplicationModel) =
        withContext(Dispatchers.Default) {
            applicationApi.createApplication(uploadApplicationModel.toApplicationRequest())
        }

    suspend fun getDetail(id: Long) = withContext(Dispatchers.Default) {
        applicationApi.getApplicationDetail(id).toApplicationDetail()
    }

    suspend fun update(application: Application) = withContext(Dispatchers.Default) {
        applicationApi.update(application)
    }
}

fun UploadApplicationModel.toApplicationRequest() =
    ApplicationRequest(
        name = name,
        developer = developer,
        rating = rating,
        ratingCount = 0,
        storeUrl = "",
        description = description,
        downloads = downloads,
        version = "",
        size = "",
        favourite = false,
        categoryId = categoryId
    )