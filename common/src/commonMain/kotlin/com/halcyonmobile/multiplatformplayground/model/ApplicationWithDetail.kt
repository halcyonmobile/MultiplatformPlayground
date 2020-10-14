package com.halcyonmobile.multiplatformplayground.model

data class ApplicationWithDetail(
    val application: Application,
    val applicationDetail: ApplicationDetail
)

fun ApplicationDetailResponse.toApplicationWithDetail() = ApplicationWithDetail(
    application = Application(id, name, developer, icon, rating, favourite, categoryId),
    applicationDetail = ApplicationDetail(
        ratingCount,
        storeUrl,
        description,
        downloads,
        version,
        size,
        screenshots
    )
)