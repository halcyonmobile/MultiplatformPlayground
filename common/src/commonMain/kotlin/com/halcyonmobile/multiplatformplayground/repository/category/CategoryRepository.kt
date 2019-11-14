package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.shared.util.getFromCacheFallbackOnRemote
import kotlinx.coroutines.flow.flowOf

internal class CategoryRepository(
    private val localSource: CategoryLocalSource,
    private val remoteSource: CategoryRemoteSource
) {

    suspend fun get() =
        getFromCacheFallbackOnRemote(localSourceOp = {
            localSource.getCategories()
        }, remoteSourceOp = {
            flowOf(remoteSource.get(0, DEFAULT_PER_PAGE).also { localSource.cacheCategoryList(it) })
        })

    companion object {
        private const val DEFAULT_PER_PAGE = 999
    }
}