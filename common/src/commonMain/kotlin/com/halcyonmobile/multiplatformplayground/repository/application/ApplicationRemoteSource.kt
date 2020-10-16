package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.shared.util.File
import com.halcyonmobile.multiplatformplayground.shared.util.toByteArray
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64

internal class ApplicationRemoteSource internal constructor(private val applicationApi: ApplicationApi) {

    suspend fun get(categoryId: Long, offset: Int, perPage: Int) =
        applicationApi.getApplicationsByCategory(offset, perPage, categoryId)

    // TODO replace uiModel with data layer model
    suspend fun create(
        applicationDetail: ApplicationDetail,
        icon: File,
        screenshots: List<File>
    ) = applicationApi.createApplication(
        applicationDetail.toApplicationRequest(
            icon,
            screenshots
        )
    )

    suspend fun getDetail(id: Long) =
        applicationApi.getApplicationDetail(id).toApplicationDetail()
}

@UseExperimental(InternalAPI::class)
fun ApplicationDetail.toApplicationRequest(icon: File, screenshotsFiles: List<File>) =
    ApplicationRequest(
        name = name,
        developer = developer,
        encodedIcon = icon.toByteArray().encodeBase64(),
        rating = rating,
        ratingCount = ratingCount,
        storeUrl = storeUrl,
        description = description,
        downloads = downloads,
        version = version,
        size = size,
        favourite = favourite,
        categoryId = categoryId,
        screenshots = screenshots.zip(screenshotsFiles) { screenshot, file ->
            screenshot.copy(image = file.toByteArray().encodeBase64())
        }
    )