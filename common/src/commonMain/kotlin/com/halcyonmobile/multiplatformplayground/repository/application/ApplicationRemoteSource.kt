package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.shared.util.toByteArray
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

internal class ApplicationRemoteSource internal constructor(private val api: ApplicationApi) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        withContext(Dispatchers.Default) {
            api.getApplicationsByCategory(offset, perPage, categoryId)
        }

    suspend fun create(uploadApplicationModel: UploadApplicationModel) {
        withContext(Dispatchers.Default) {
            val appId = api.createApplication(uploadApplicationModel.toApplicationRequest())
            val fileRequests = uploadApplicationModel.screenshots.mapIndexed { index, screenshot ->
                async {
                    api.postScreenshot(appId, "${appId}_screenshot_$index", screenshot)
                }
            } + async { api.postIcon(appId, uploadApplicationModel.icon) }

            fileRequests.awaitAll()
        }
    }

    suspend fun getDetail(id: Long) = withContext(Dispatchers.Default) {
        api.getApplicationDetail(id).toApplicationDetail()
    }

    suspend fun update(application: Application) = withContext(Dispatchers.Default) {
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