package com.halcyonmobile.multiplatformplayground.storage.file

interface FileStorage {

    suspend fun save(name: String, encodedFile: String): String
}