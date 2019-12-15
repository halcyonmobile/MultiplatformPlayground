package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.halcyonmobile.multiplatformplayground.shared.util.*
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

internal object ApplicationTable : Table("applications") {
    val id = long(APP_ID).autoIncrement().primaryKey()
    val name = varchar(APP_NAME, 50)
    val developer = varchar(APP_DEVELOPER, 50)
    val icon = varchar(APP_ICON, 255).nullable()
    val rating = decimal(APP_RATING, 3, 1).nullable()
    val ratingCount = integer(APP_RATING_COUNT).nullable()
    val storeUrl = varchar(APP_STORE_URL, 255).nullable()
    val description = varchar(APP_DESCRIPTION, 255).nullable()
    val downloads = varchar(APP_DOWNLOADS, 50).nullable()
    val version = varchar(APP_VERSION, 50).nullable()
    val size = varchar(APP_SIZE, 50).nullable()
    val favourite = bool(APP_FAVOURITE).default(false)
    val categoryId =
        long(APP_CATEGORY_ID).references(CategoryTable.id, onDelete = ReferenceOption.SET_NULL)
            .nullable()
}

internal object CategoryTable : Table("categories") {
    val id = long(CATEGORY_ID).autoIncrement().primaryKey()
    val name = varchar(CATEGORY_NAME, 50)
    val icon = varchar(CATEGORY_ICON, 255)
}

internal object ScreenshotTable : Table("screenshots") {
    val id = long(SCREENSHOT_ID).autoIncrement().primaryKey()
    val name = varchar(SCREENSHOT_NAME, 50).nullable()
    val image = varchar(SCREENSHOT_IMAGE, 255)
    val applicationId =
        long(SCREENSHOT_APP_ID).references(ApplicationTable.id, onDelete = ReferenceOption.CASCADE)
}

fun ResultRow.mapRowToApplication(category: Category? = null, screenshots: List<Screenshot>) =
    Application(
        id = get(ApplicationTable.id),
        name = get(ApplicationTable.name),
        developer = get(ApplicationTable.developer),
        icon = get(ApplicationTable.icon),
        rating = get(ApplicationTable.rating)?.toFloat(),
        ratingCount = get(ApplicationTable.ratingCount),
        storeUrl = get(ApplicationTable.storeUrl),
        description = get(ApplicationTable.description),
        downloads = get(ApplicationTable.downloads),
        version = get(ApplicationTable.version),
        size = get(ApplicationTable.size),
        favourite = get(ApplicationTable.favourite),
        category = category,
        screenshots = screenshots
    )

fun ResultRow.mapRowToCategory() = Category(
    id = get(CategoryTable.id),
    name = get(CategoryTable.name),
    icon = get(CategoryTable.icon)
)

fun ResultRow.mapRowToScreenshot() = Screenshot(
    id = get(ScreenshotTable.id),
    name = get(ScreenshotTable.name),
    image = get(ScreenshotTable.image)
)
