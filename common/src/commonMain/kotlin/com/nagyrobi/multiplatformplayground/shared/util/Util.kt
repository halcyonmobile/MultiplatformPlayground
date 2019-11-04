package com.nagyrobi.multiplatformplayground.shared.util

import com.nagyrobi.multiplatformplayground.repository.NoCacheFoundException

suspend fun <T> getFromCacheFallbackOnRemote(
    localSourceOp: suspend () -> T,
    remoteSourceOp: suspend () -> T
) =
    try {
        localSourceOp()
    } catch (e: NoCacheFoundException) {
        remoteSourceOp()
    }