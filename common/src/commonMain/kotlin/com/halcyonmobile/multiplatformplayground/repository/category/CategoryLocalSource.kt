package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.repository.NoCacheFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf

@UseExperimental(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class CategoryLocalSource {

    private val _categories = ConflatedBroadcastChannel<List<Category>>(emptyList())
    val categories: Flow<List<Category>>
        get() = _categories.asFlow().distinctUntilChanged()


    fun cacheCategoryList(categories: List<Category>) = _categories.offer(categories)

}