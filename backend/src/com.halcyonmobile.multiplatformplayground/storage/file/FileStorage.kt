package com.halcyonmobile.multiplatformplayground.storage.file

abstract class FileStorage {
    protected abstract val screenshotPath: String
    protected abstract val appIconPath: String
    protected abstract val categoryIconPath: String

    fun uploadScreenshot(bytes: ByteArray, name: String) =
        upload(bytes, name, screenshotPath)

    fun uploadIcon(bytes: ByteArray, name: String) = upload(bytes, name, appIconPath)

    fun uploadCategory(bytes: ByteArray, name: String) = upload(bytes, name, categoryIconPath)

    protected abstract fun upload(bytes: ByteArray, name: String, uploadPath: String): String
}