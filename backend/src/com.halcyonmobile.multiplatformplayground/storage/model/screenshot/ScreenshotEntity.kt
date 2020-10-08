package com.halcyonmobile.multiplatformplayground.storage.model.screenshot

import com.halcyonmobile.multiplatformplayground.storage.model.application.ApplicationEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ScreenshotEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ScreenshotEntity>(ScreenshotTable)

    val name by ScreenshotTable.name
    val image by ScreenshotTable.image
    var application by ApplicationEntity referencedOn ScreenshotTable.application
}