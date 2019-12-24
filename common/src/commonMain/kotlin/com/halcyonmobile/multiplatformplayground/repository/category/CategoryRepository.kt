package com.halcyonmobile.multiplatformplayground.repository.category

import kotlinx.coroutines.flow.first

class CategoryRepository internal constructor(
    private val localSource: CategoryLocalSource,
    private val remoteSource: CategoryRemoteSource
) {
    val stream = localSource.categories

    internal suspend fun get() =
        localSource.categories.first().let {
            if (it.isEmpty()) fetch()
            else it
        }

    internal suspend fun fetch() =
        remoteSource.get(0, DEFAULT_PER_PAGE)
            .also { localSource.cacheCategoryList(it) }

    companion object {
        private const val DEFAULT_PER_PAGE = 999
    }
}