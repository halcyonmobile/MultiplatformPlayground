package com.halcyonmobile.multiplatformplayground.util

import io.ktor.http.content.*
import java.io.File
import java.time.LocalDateTime

fun PartData.FileItem.toFile(name: String) = File("/$name").also { file ->
    streamProvider().use { input ->
        file.outputStream().buffered().use { output ->
            input.copyTo(output)
        }
    }
}

private val PartData.FileItem.baseName
    get() = "${LocalDateTime.now()}_${originalFileName}.${File(originalFileName ?: "").extension}"

val PartData.FileItem.iconName
    get() = "${baseName}_ic"

val PartData.FileItem.imageName
    get() = "${baseName}_img"