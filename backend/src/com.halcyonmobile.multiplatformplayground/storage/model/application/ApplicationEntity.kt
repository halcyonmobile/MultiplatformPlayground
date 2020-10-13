package com.halcyonmobile.multiplatformplayground.storage.model.application

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.ApplicationDetailResponse
import com.halcyonmobile.multiplatformplayground.storage.model.category.CategoryEntity
import com.halcyonmobile.multiplatformplayground.storage.model.screenshot.ScreenshotEntity
import com.halcyonmobile.multiplatformplayground.storage.model.screenshot.ScreenshotTable
import com.halcyonmobile.multiplatformplayground.storage.model.screenshot.toScreenshot
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ApplicationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ApplicationEntity>(ApplicationTable)

    var name by ApplicationTable.name
    var developer by ApplicationTable.developer
    var icon by ApplicationTable.icon
    var rating by ApplicationTable.rating
    var ratingCount by ApplicationTable.ratingCount
    var storeUrl by ApplicationTable.storeUrl
    var description by ApplicationTable.description
    var downloads by ApplicationTable.downloads
    var version by ApplicationTable.version
    var size by ApplicationTable.size
    var favourite by ApplicationTable.favourite
    var category by CategoryEntity referencedOn ApplicationTable.category
    val screenshots by ScreenshotEntity referrersOn ScreenshotTable.application
}

fun ApplicationEntity.toApplicationDetailResponse() = ApplicationDetailResponse(
    id.value.toLong(),
    name,
    developer,
    icon,
    rating.toFloat(),
    ratingCount,
    storeUrl,
    description,
    downloads,
    version,
    size,
    favourite,
    category.id.value.toLong(),
    screenshots.map { it.toScreenshot() }
)


fun ApplicationEntity.toApplication() =
    Application(id.value.toLong(), name, icon, developer, favourite, category.id.value.toLong())