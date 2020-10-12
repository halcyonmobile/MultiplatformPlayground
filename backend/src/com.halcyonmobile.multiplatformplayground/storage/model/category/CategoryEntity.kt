package com.halcyonmobile.multiplatformplayground.storage.model.category

import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.storage.model.application.ApplicationEntity
import com.halcyonmobile.multiplatformplayground.storage.model.application.ApplicationTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CategoryEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryEntity>(CategoryTable)

    var name by CategoryTable.name
    var icon by CategoryTable.icon
    val applications by ApplicationEntity referrersOn ApplicationTable.category
}

fun CategoryEntity.toCategory() = Category(id.value.toLong(), name, icon)