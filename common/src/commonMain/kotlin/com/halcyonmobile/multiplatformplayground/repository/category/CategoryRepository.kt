package com.halcyonmobile.multiplatformplayground.repository.category

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class CategoryRepository(
    private val remoteSource: CategoryRemoteSource,
    private val localSource: CategoryLocalSource
) {
    suspend fun get() =
        localSource.getCategories().let { if (it.isEmpty()) fetch() else it }

    suspend fun get(id: Long) = localSource.get(id)

    suspend fun fetch() = remoteSource.get(0, DEFAULT_PER_PAGE).onEach { localSource.insert(it) }

    companion object {
        private const val DEFAULT_PER_PAGE = 999
    }
}