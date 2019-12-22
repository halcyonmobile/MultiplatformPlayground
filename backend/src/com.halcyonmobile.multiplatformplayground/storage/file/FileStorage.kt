package com.halcyonmobile.multiplatformplayground.storage.file

abstract class FileStorage {
    protected abstract val screenshotPath: String
    protected abstract val iconPath: String

    fun uploadScreenshot(bytes: ByteArray, name: String) =
        upload(bytes, name, screenshotPath)

    fun uploadIcon(bytes: ByteArray, name: String) = upload(bytes, name, iconPath)

    protected abstract fun upload(bytes: ByteArray, name: String, uploadPath: String): String
}