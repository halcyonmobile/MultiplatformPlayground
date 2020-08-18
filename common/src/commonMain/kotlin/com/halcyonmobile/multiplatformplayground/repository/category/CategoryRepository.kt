package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.model.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged


@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CategoryRepository internal constructor(
    private val remoteSource: CategoryRemoteSource
) {

    private val _categories = ConflatedBroadcastChannel<List<Category>>()
    val categories: Flow<List<Category>>
        get() = _categories.asFlow().distinctUntilChanged()

    internal suspend fun get() = _categories.valueOrNull ?: fetch()

    internal suspend fun fetch() = remoteSource.get(0, DEFAULT_PER_PAGE)
        .also { _categories.offer(it) }

    companion object {
        private const val DEFAULT_PER_PAGE = 999
    }
}