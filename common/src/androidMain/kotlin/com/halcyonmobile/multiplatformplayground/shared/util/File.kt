package com.halcyonmobile.multiplatformplayground.shared.util

actual typealias File = java.io.File

actual fun File.toByteArray() = readBytes()