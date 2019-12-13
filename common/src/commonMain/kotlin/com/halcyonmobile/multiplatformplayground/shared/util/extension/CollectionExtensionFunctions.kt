package com.halcyonmobile.multiplatformplayground.shared.util.extension

fun <T> List<T>.safeSubList(fromIndex: Int, toIndex: Int) =
    drop(fromIndex).take(toIndex - fromIndex)
