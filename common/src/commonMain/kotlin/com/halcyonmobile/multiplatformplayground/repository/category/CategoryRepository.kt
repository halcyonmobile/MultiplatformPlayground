package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.shared.util.getFromCacheFallbackOnRemote

internal class CategoryRepository(
    private val localSource: CategoryLocalSource,
    private val remoteSource: CategoryRemoteSource
) {

    suspend fun get() =
        getFromCacheFallbackOnRemote(localSourceOp = {
            localSource.getCategories()
        }, remoteSourceOp = {
            remoteSource.get(0, DEFAULT_PER_PAGE).also { localSource.cacheCategoryList() }
        })

    companion object {
        private const val DEFAULT_PER_PAGE = 999
    }
}