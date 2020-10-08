package com.halcyonmobile.multiplatformplayground.storage.model.application

import com.halcyonmobile.multiplatformplayground.shared.util.*
import com.halcyonmobile.multiplatformplayground.storage.model.category.CategoryTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

internal object ApplicationTable : IntIdTable("applications") {
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
    val category = reference(
        APP_CATEGORY_ID,
        CategoryTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}