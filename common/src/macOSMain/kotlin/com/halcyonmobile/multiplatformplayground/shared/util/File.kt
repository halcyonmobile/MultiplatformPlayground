package com.halcyonmobile.multiplatformplayground.shared.util

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.useContents
import kotlinx.cinterop.usePinned
import platform.AppKit.NSBitmapImageRep
import platform.AppKit.NSImage
import platform.Foundation.NSData
import platform.AppKit.NSPNGFileType
import platform.AppKit.representationUsingType
import platform.Foundation.NSMakeRect
import platform.CoreGraphics.CGSize
import platform.CoreGraphics.CGFloat
import platform.posix.memcpy

@Suppress("CONFLICTING_OVERLOADS")
actual typealias ImageFile = NSImage

actual fun ImageFile.toByteArray() = NSImagePNGRepresentation(this)?.toByteArray() ?: emptyArray<Byte>().toByteArray()

@OptIn(ExperimentalUnsignedTypes::class)
private fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }
}

private fun NSImagePNGRepresentation(image: ImageFile): NSData? {
    image.lockFocus()
    val ref = NSBitmapImageRep(NSMakeRect(0.0, 0.0, image.size.useContents { width }, image.size.useContents { height }))
    image.unlockFocus()
    return ref.representationUsingType(NSPNGFileType, emptyMap<Any?, Any>())
}
