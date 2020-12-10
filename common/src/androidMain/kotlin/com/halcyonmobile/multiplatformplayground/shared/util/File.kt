package com.halcyonmobile.multiplatformplayground.shared.util

actual typealias ImageFile = java.io.File

actual fun ImageFile.toByteArray() = readBytes()