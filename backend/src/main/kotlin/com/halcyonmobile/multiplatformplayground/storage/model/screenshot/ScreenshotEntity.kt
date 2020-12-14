package com.halcyonmobile.multiplatformplayground.storage.model.screenshot

import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.halcyonmobile.multiplatformplayground.storage.model.application.ApplicationEntity
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ScreenshotEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ScreenshotEntity>(ScreenshotTable)

    var name by ScreenshotTable.name
    var image by ScreenshotTable.image
    var application by ApplicationEntity referencedOn ScreenshotTable.application
}

fun ScreenshotEntity.toScreenshot() = Screenshot(id.value.toLong(), name, image)