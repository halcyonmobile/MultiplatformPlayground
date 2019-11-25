package com.halcyonmobile.multiplatformplayground.repository.application

import com.halcyonmobile.multiplatformplayground.shared.util.getFromCacheFallbackOnRemote
import kotlinx.coroutines.flow.flowOf

internal class ApplicationRepository(
    private val localSource: ApplicationLocalSource,
    private val remoteSource: ApplicationRemoteSource
) {
    val favouritesStream = localSource.favouritesStream

    suspend fun getById(id: Long) = getFromCacheFallbackOnRemote(localSourceOp = {
        localSource.getById(id)
    }, remoteSourceOp = {
        getApplicationWithDetailFromRemoteAndCacheToMemory(id)
    })

    suspend fun getDetailById(id: Long) = getFromCacheFallbackOnRemote(localSourceOp = {
        localSource.getDetailById(id)
    }, remoteSourceOp = {
        flowOf(getApplicationWithDetailFromRemoteAndCacheToMemory(id))
    })

    suspend fun getByCategory(id: Long, page: Int = FIRST_PAGE_OFFSET, perPage: Int = DEFAULT_PER_PAGE) =
        getFromCacheFallbackOnRemote(
            localSourceOp = { localSource.getByCategory(id, page, perPage) },
            remoteSourceOp = {
                remoteSource.get(id, page, perPage).also {
                    localSource.cacheByCategory(id, page, it)
                }
            })

    private suspend fun getApplicationWithDetailFromRemoteAndCacheToMemory(appId: Long) =
        remoteSource.getDetail(appId).also {
            localSource.cacheApplicationWithDetail(it)
        }

    // todo add create
    // todo update paging

    suspend fun updateFavourites(appId: Long, isFavorite: Boolean) =
        localSource.updateFavourites(appId, isFavorite)


    companion object {
        const val DEFAULT_PER_PAGE = 10
        private const val FIRST_PAGE_OFFSET = 1
    }
}