package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.shared.util.extension.firstOrNull
import com.halcyonmobile.multiplatformplayground.shared.util.getFromCacheFallbackOnRemote
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class CategoryRepository internal constructor(
    private val localSource: CategoryLocalSource,
    private val remoteSource: CategoryRemoteSource
) {
    val stream = localSource.getCategories()

    internal suspend fun get() =
        localSource.getCategories().firstOrNull() ?: fetch()

    internal suspend fun fetch() =
        remoteSource.get(0, DEFAULT_PER_PAGE)
            .also { localSource.cacheCategoryList(it) }


    companion object {
        private const val DEFAULT_PER_PAGE = 999
    }
}