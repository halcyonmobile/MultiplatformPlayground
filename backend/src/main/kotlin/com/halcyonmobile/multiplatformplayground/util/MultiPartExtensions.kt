package com.halcyonmobile.multiplatformplayground.util

import io.ktor.http.content.PartData
import io.ktor.http.content.streamProvider
import java.io.File

fun PartData.FileItem.toFile() = File(originalFileName!!).also { file ->
    streamProvider().use { input ->
        file.outputStream().buffered().use { output ->
            input.copyTo(output)
        }
    }
}
