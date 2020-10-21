package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.shared.util.File
import com.halcyonmobile.multiplatformplayground.shared.util.toByteArray
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64

internal class ApplicationRemoteSource internal constructor(private val applicationApi: ApplicationApi) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        applicationApi.getApplicationsByCategory(offset, perPage, categoryId)

    suspend fun create(uploadApplicationModel: UploadApplicationModel) =
        applicationApi.createApplication(uploadApplicationModel.toApplicationRequest())

    suspend fun getDetail(id: Long) =
        applicationApi.getApplicationDetail(id).toApplicationDetail()
}

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