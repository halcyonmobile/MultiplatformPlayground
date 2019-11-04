package com.nagyrobi.multiplatformplayground.repository.application

import com.nagyrobi.multiplatformplayground.shared.util.getFromCacheFallbackOnRemote

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
        getApplicationWithDetailFromRemoteAndCacheToMemory(id)
    })

    suspend fun getByCategory(id: Long) = getFromCacheFallbackOnRemote(
        localSourceOp = { localSource.getByCategory(id, DEFAULT_PER_PAGE) },
        remoteSourceOp = { this.reloadByCategory(id) })

    suspend fun loadMoreByCategory(categoryId: Long, offset: Int) =
        getFromCacheFallbackOnRemote(localSourceOp = {
            localSource.getMoreByCategory(categoryId, offset, DEFAULT_PER_PAGE)
        }, remoteSourceOp = {
            remoteSource.get(categoryId, offset, DEFAULT_PER_PAGE).also {
                localSource.addMoreByCategory(categoryId, it)
            }
        })

    suspend fun reloadByCategory(categoryId: Long) =
        remoteSource.get(categoryId, FIRST_PAGE_OFFSET, DEFAULT_PER_PAGE).also {
            localSource.replaceByCategory(categoryId, it)
        }

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
        private const val SECOND_PAGE = 2
    }
}