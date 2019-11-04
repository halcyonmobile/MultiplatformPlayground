package com.halcyonmobile.multiplatformplayground.shared.util

import com.halcyonmobile.multiplatformplayground.repository.NoCacheFoundException

suspend fun <T> getFromCacheFallbackOnRemote(
    localSourceOp: suspend () -> T,
    remoteSourceOp: suspend () -> T
) =
    try {
        localSourceOp()
    } catch (e: NoCacheFoundException) {
        remoteSourceOp()
    }