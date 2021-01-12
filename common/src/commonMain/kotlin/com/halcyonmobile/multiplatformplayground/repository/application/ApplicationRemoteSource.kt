package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.shared.util.DispatcherProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

internal class ApplicationRemoteSource internal constructor(
    private val api: ApplicationApi,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        withContext(dispatcherProvider.io) {
            api.getApplicationsByCategory(offset, perPage, categoryId)
        }

    suspend fun create(uploadApplicationModel: UploadApplicationModel) {
        withContext(dispatcherProvider.io) {
            val appId = api.createApplication(uploadApplicationModel.toApplicationRequest())
            val fileRequests = uploadApplicationModel.screenshots.mapIndexed { index, screenshot ->
                async {
                    api.postScreenshot(appId, "${appId}_screenshot_$index", screenshot)
                }
            } + async { api.postIcon(appId, uploadApplicationModel.icon) }

            fileRequests.awaitAll()
        }
    }

    suspend fun getDetail(id: Long) = withContext(dispatcherProvider.io) {
        api.getApplicationDetail(id).toApplicationDetail()
    }

    suspend fun update(application: Application) = withContext(dispatcherProvider.io) {
        api.update(application)
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