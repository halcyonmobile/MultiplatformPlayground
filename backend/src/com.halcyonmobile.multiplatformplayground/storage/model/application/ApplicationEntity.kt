package com.halcyonmobile.multiplatformplayground.storage.model.application

import com.halcyonmobile.multiplatformplayground.storage.model.category.CategoryEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ApplicationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ApplicationEntity>(ApplicationTable)

    val name by ApplicationTable.name
    val developer by ApplicationTable.developer
    val icon by ApplicationTable.icon
    val rating by ApplicationTable.rating
    val ratingCount by ApplicationTable.ratingCount
    val storeUrl by ApplicationTable.storeUrl
    val description by ApplicationTable.description
    val downloads by ApplicationTable.downloads
    val version by ApplicationTable.version
    val size by ApplicationTable.size
    val favourite by ApplicationTable.favourite
    var category by CategoryEntity referencedOn ApplicationTable.category
}