package com.halcyonmobile.multiplatformplayground.storage.model.category

import com.halcyonmobile.multiplatformplayground.shared.util.CATEGORY_ICON
import com.halcyonmobile.multiplatformplayground.shared.util.CATEGORY_NAME
import org.jetbrains.exposed.dao.id.IntIdTable

object CategoryTable : IntIdTable("categories") {
    val name = varchar(CATEGORY_NAME, 50)
    val icon = varchar(CATEGORY_ICON, 255)
}