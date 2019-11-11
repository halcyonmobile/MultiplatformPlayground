package com.halcyonmobile.multiplatformplayground.storage

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import org.jetbrains.squash.definition.*
import org.jetbrains.squash.results.ResultRow
import org.jetbrains.squash.results.get

internal object ApplicationTable : TableDefinition() {
    val id = long("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val developer = varchar("developer", 50)
    val icon = varchar("icon", 255).nullable()
    val rating = decimal("rating", 1, 3).nullable()
    val ratingCount = integer("ratingCount").nullable()
    val storeUrl = varchar("storeUrl", 255).nullable()
    val description = varchar("description", 255).nullable()
    val downloads = varchar("downloads", 50).nullable()
    val version = varchar("version", 50).nullable()
    val size = varchar("size", 50).nullable()
    val favourite = bool("favourite").default(false)
    val categoryId = reference(CategoryTable.id, "category_id").nullable()
}

internal object CategoryTable : TableDefinition() {
    val id = long("id").autoIncrement().primaryKey()
    val name = varchar("name", 50)
    val icon = varchar("icon", 255).nullable()
}

internal object ScreenshotTable : TableDefinition() {
    val id = long("id").autoIncrement().primaryKey()
    val name = varchar("name", 50).nullable()
    val image = varchar("image", 255)
    val applicationId = reference(ApplicationTable.id, "application_id")
}

fun ResultRow.mapRowToApplication(category: Category? = null, screenshots: List<Screenshot>) =
    Application(
        id = get("id"),
        name = get("name"),
        developer = get("developer"),
        icon = get("icon"),
        rating = get("rating"),
        ratingCount = get("ratingCount"),
        storeUrl = get("storeUrl"),
        description = get("description"),
        downloads = get("downloads"),
        version = get("version"),
        size = get("size"),
        favourite = get("favourite"),
        category = category,
        screenshots = screenshots
    )

fun ResultRow.mapRowToCategory() = Category(
    id = get("id"),
    name = get("name"),
    icon = get("name")
)

fun ResultRow.mapRowToScreenshot() = Screenshot(
    id = get("id"),
    name = get("name"),
    image = get("image")
)
