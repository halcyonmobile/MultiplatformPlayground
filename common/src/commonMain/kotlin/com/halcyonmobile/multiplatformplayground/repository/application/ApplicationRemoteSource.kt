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

    suspend fun create(
        applicationWithDetail: ApplicationWithDetail,
        icon: File,
        screenshots: List<File>
    ) = applicationApi.createApplication(
        applicationWithDetail.toApplicationRequest(
            icon,
            screenshots
        )
    )

    suspend fun getDetail(id: Long) =
        applicationApi.getApplicationDetails(id).toApplicationWithDetail()
}

@UseExperimental(InternalAPI::class)
fun ApplicationWithDetail.toApplicationRequest(icon: File, screenshots: List<File>) =
    ApplicationRequest(
        name = application.name,
        developer = application.developer,
        encodedIcon = icon.toByteArray().encodeBase64(),
        rating = application.rating,
        ratingCount = applicationDetail.ratingCount,
        storeUrl = applicationDetail.storeUrl,
        description = applicationDetail.description,
        downloads = applicationDetail.downloads,
        version = applicationDetail.version,
        size = applicationDetail.size,
        favourite = application.favourite,
        categoryId = application.categoryId,
        screenshots = applicationDetail.screenshots.zip(screenshots) { screenshot, file ->
            screenshot.copy(image = file.toByteArray().encodeBase64())
        }
    )