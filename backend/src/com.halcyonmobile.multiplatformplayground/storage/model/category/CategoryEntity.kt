package com.halcyonmobile.multiplatformplayground.storage.model.category

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CategoryEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CategoryEntity>(CategoryTable)

    val name by CategoryTable.name
    val icon by CategoryTable.icon
}