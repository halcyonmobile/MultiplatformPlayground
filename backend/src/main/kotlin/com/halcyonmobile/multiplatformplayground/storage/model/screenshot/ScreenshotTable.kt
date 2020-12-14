package com.halcyonmobile.multiplatformplayground.storage.model.screenshot

import com.halcyonmobile.multiplatformplayground.shared.util.SCREENSHOT_APP_ID
import com.halcyonmobile.multiplatformplayground.shared.util.SCREENSHOT_IMAGE
import com.halcyonmobile.multiplatformplayground.shared.util.SCREENSHOT_NAME
import com.halcyonmobile.multiplatformplayground.storage.model.application.ApplicationTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object ScreenshotTable : IntIdTable("screenshots") {
    val name = varchar(SCREENSHOT_NAME, 50)
    val image = varchar(SCREENSHOT_IMAGE, 255)
    val application = reference(
        SCREENSHOT_APP_ID,
        ApplicationTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}