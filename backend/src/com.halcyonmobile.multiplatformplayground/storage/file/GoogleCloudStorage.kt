package com.halcyonmobile.multiplatformplayground.storage.file

import com.google.cloud.storage.Acl
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions

class GoogleCloudStorage : FileStorage() {

    override val screenshotPath = "multiplatform_screenshot"
    override val appIconPath = "multiplatform_app"
    override val categoryIconPath = "multiplatform_category"

    private val storage = StorageOptions.getDefaultInstance().service

    // todo update it to writer
    override fun upload(bytes: ByteArray, name: String, uploadPath: String): String =
        storage.create(
            BlobInfo.newBuilder(uploadPath, name)
                .setAcl(listOf(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                .build()
            , bytes
        ).mediaLink

}
