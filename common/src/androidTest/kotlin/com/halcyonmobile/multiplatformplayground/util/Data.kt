package com.halcyonmobile.multiplatformplayground.util

import com.halcyonmobile.multiplatformplayground.db.Categories
import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import com.halcyonmobile.multiplatformplayground.shared.util.PER_PAGE
import com.halcyonmobile.multiplatformplayground.shared.util.toByteArray
import io.ktor.util.*

// TODO restructure this
val category = Category(
    id = 1,
    name = "Category 1",
    icon = "icon 1"
)

val category2 = Category(
    id = 2,
    name = "Category 2",
    icon = "icon 2"
)

val categoryEntity = Categories(
    id = 1,
    name = "Category 1",
    icon = "icon 1"
)

val applicationData = Application(
    id = 1,
    name = "application 1",
    icon = "",
    developer = "developer 1",
    rating = 4.5f,
    favourite = true,
    categoryId = 0
)

val fullPageApplicationData = (0..10).map {
    Application(
        id = it.toLong(),
        name = "application $it",
        icon = "",
        developer = "developer $it",
        rating = 4.5f,
        favourite = it % 2 == 0,
        categoryId = 0
    )
}

val screenshot = Screenshot(0, "image 1", "imageUrl")

val applicationDetailResponseData = ApplicationDetailResponse(
    id = 1,
    name = "application 1",
    developer = "developer 1",
    icon = "",
    rating = 4.5f,
    ratingCount = 100,
    storeUrl = "https://",
    description = "Lorem ipsum",
    downloads = "4.2M",
    version = "4.1.1",
    size = "427 MB",
    favourite = true,
    categoryId = 0,
    screenshots = listOf(screenshot)
)

val applicationDetailData = ApplicationDetail(
    id = 1,
    name = "application 1",
    developer = "developer 1",
    icon = "",
    rating = 4.5f,
    ratingCount = 100,
    storeUrl = "https://",
    description = "Lorem ipsum",
    downloads = "4.2M",
    version = "4.1.1",
    size = "427 MB",
    favourite = true,
    categoryId = 0,
    screenshots = listOf(screenshot)
)

val applicationUiModelData = ApplicationUiModel.App(
    id = 1,
    name = "application 1",
    developer = "developer 1",
    icon = "",
    rating = 4.5f,
    favourite = true,
    categoryId = 0
)

val applicationDetailUiModelData = ApplicationDetailUiModel(
    id = 1,
    name = "application 1",
    developer = "developer 1",
    icon = "",
    rating = 4.5f,
    ratingCount = 100,
    storeUrl = "https://",
    description = "Lorem ipsum",
    downloads = "4.2M",
    version = "4.1.1",
    size = "427 MB",
    favourite = true,
    categoryId = 0,
    screenshots = listOf(screenshot)
)

fun getUploadApplication(icon: ImageFile, screenshots: List<ImageFile>) = UploadApplicationModel(
    name = "application 1",
    developer = "developer 1",
    icon = icon,
    rating = 4.5f,
    description = "Lorem ipsum",
    downloads = "4.2M",
    categoryId = 0,
    screenshots = screenshots
)

@OptIn(InternalAPI::class)
fun getUploadApplicationRequest(icon: ImageFile, screenshots: List<ImageFile>) = ApplicationRequest(
    name = "application 1",
    developer = "developer 1",
    encodedIcon = icon.toByteArray().encodeBase64(),
    rating = 4.5f,
    ratingCount = 0,
    storeUrl = "",
    description = "Lorem ipsum",
    downloads = "4.2M",
    version = "",
    size = "",
    categoryId = 0,
    screenshots = screenshots.map { Screenshot(image = it.toByteArray().encodeBase64(), name = "") }
)