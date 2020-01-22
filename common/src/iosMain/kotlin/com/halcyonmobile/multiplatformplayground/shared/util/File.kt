package com.halcyonmobile.multiplatformplayground.shared.util

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSFileHandle
import platform.posix.memcpy

actual typealias File = NSFileHandle

actual fun File.toByteArray() = readDataToEndOfFile().toByteArray()

@UseExperimental(ExperimentalUnsignedTypes::class)
private fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }
}