package com.halcyonmobile.multiplatformplayground.storage.model.application

import com.halcyonmobile.multiplatformplayground.shared.util.*
import com.halcyonmobile.multiplatformplayground.storage.model.category.CategoryTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

internal object ApplicationTable : IntIdTable("applications") {
    val name = varchar(APP_NAME, 50)
    val developer = varchar(APP_DEVELOPER, 50)
    val icon = varchar(APP_ICON, 255)
    val rating = decimal(APP_RATING, 3, 1)
    val ratingCount = integer(APP_RATING_COUNT)
    val storeUrl = varchar(APP_STORE_URL, 255)
    val description = varchar(APP_DESCRIPTION, 255)
    val downloads = varchar(APP_DOWNLOADS, 50)
    val version = varchar(APP_VERSION, 50)
    val size = varchar(APP_SIZE, 50)
    val favourite = bool(APP_FAVOURITE).default(false)
    val category = reference(
        APP_CATEGORY_ID,
        CategoryTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}