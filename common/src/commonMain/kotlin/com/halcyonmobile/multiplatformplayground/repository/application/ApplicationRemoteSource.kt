package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.shared.util.DispatcherProvider
import com.halcyonmobile.multiplatformplayground.shared.util.toByteArray
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ApplicationRemoteSource internal constructor(
    private val applicationApi: ApplicationApi,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        withContext(dispatcherProvider.io) {
            applicationApi.getApplicationsByCategory(offset, perPage, categoryId)
        }

    suspend fun create(uploadApplicationModel: UploadApplicationModel) =
        withContext(dispatcherProvider.io) {
            applicationApi.createApplication(uploadApplicationModel.toApplicationRequest())
        }

    suspend fun getDetail(id: Long) = withContext(dispatcherProvider.io) {
        applicationApi.getApplicationDetail(id).toApplicationDetail()
    }

    suspend fun update(application: Application) = withContext(dispatcherProvider.io) {
        applicationApi.update(application)
    }
}

// TODO add missing parameters also
@OptIn(InternalAPI::class)
fun UploadApplicationModel.toApplicationRequest() =
    ApplicationRequest(
        name = name,
        developer = developer,
        encodedIcon = icon.toByteArray().encodeBase64(),
        rating = rating,
        ratingCount = 0,
        storeUrl = "",
        description = description,
        downloads = downloads,
        version = "",
        size = "",
        favourite = false,
        categoryId = categoryId,
        screenshots = screenshots.map {
            Screenshot(image = it.toByteArray().encodeBase64(), name = "")
        }
    )