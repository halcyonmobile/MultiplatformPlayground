package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

internal object ApplicationTable : Table("applications") {
    val id = long("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val developer = varchar("developer", 50)
    val icon = varchar("icon", 255).nullable()
    val rating = decimal("rating", 3, 1).nullable()
    val ratingCount = integer("ratingCount").nullable()
    val storeUrl = varchar("storeUrl", 255).nullable()
    val description = varchar("description", 255).nullable()
    val downloads = varchar("downloads", 50).nullable()
    val version = varchar("version", 50).nullable()
    val size = varchar("size", 50).nullable()
    val favourite = bool("favourite").default(false)
    val categoryId =
        long("category_id").references(CategoryTable.id, onDelete = ReferenceOption.SET_NULL)
            .nullable()
}

internal object CategoryTable : Table("categories") {
    val id = long("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val icon = varchar("icon", 255)
}

internal object ScreenshotTable : Table("screenshots") {
    val id = long("id").autoIncrement().primaryKey()
    val name = varchar("name", 50).nullable()
    val image = varchar("image", 255)
    val applicationId =
        long("application_id").references(ApplicationTable.id, onDelete = ReferenceOption.CASCADE)
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
