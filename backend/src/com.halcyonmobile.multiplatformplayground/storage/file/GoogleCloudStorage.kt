package com.halcyonmobile.multiplatformplayground.storage.file

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.Acl
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import io.ktor.application.Application
import java.io.FileInputStream

class GoogleCloudStorage(application: Application) : FileStorage() {

    override val screenshotPath = "multiplatform_screenshot"
    override val appIconPath = "multiplatform_app"
    override val categoryIconPath = "multiplatform_category"

    private val credentials = GoogleCredentials.fromStream(
        application.environment.classLoader.getResourceAsStream(KEY_FILE_NAME)
    )
    private val storage = StorageOptions.newBuilder().setCredentials(credentials)
        .setProjectId("multiplatformplayground").build().service

    // todo update it to writer
    override suspend fun upload(bytes: ByteArray, name: String, uploadPath: String): String =
        storage.create(
            BlobInfo.newBuilder(uploadPath, name)
                .setContentType("text/plain")
                .setAcl(listOf(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))
                .build()
            , bytes
        ).mediaLink

    companion object {
        private const val KEY_FILE_NAME = "multiplatformplayground-31d4d63c0077.json"
    }
}
