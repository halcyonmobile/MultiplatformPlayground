package com.halcyonmobile.multiplatformplayground.util

import io.ktor.http.content.*
import java.io.File
import java.time.LocalDateTime

fun PartData.FileItem.toFile() = File(originalFileName!!).also { file ->
    streamProvider().use { input ->
        file.outputStream().buffered().use { output ->
            input.copyTo(output)
        }
    }
}
