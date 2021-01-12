package com.halcyonmobile.multiplatformplayground.storage.file

import java.io.File

interface FileStorage {

    suspend fun save(file: File): String
}