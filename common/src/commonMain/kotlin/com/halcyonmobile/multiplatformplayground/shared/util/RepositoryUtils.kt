package com.halcyonmobile.multiplatformplayground.shared.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach

/**
 * Tries to retrieve the data from the cache, if there is no cache calls the remote operation instead
 * @param localSourceOp the operation, which gets the data from the local source
 * @param remoteSourceOp the operation, which fetches the data from the remote source
 */
suspend fun <T> getFromCacheFallbackOnRemote(
    localSourceOp: suspend () -> T?,
    remoteSourceOp: suspend () -> T
) = localSourceOp() ?: remoteSourceOp()

inline fun <T> getStreamFromCacheFallbackOnRemote(
    localStream: Flow<T?>,
    crossinline remoteSourceOp: suspend () -> T,
    crossinline cacheOperation: suspend (T) -> Unit
) = localStream.onEach {
    if (it == null) {
        remoteSourceOp().also { remoteResult ->
            cacheOperation(remoteResult)
        }
    }
}
    .filter { it != null }

inline fun <T> getStreamFromCacheFallbackOnRemote(
    limit: Int,
    localStream: Flow<List<T>>,
    crossinline remoteSourceOp: suspend () -> List<T>,
    crossinline cacheOperation: suspend (List<T>) -> Unit
) = localStream.onEach {
    if (it.size < limit) {
        remoteSourceOp().also { remoteResult ->
            cacheOperation(remoteResult)
        }
    }
}