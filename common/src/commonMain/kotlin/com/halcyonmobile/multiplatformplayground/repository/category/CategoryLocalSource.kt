package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.repository.NoCacheFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

// TODO solve this
// Currently the problem is, that you can't expose an api with Flows to iOS
internal class CategoryLocalSource {
    fun getCategories(): Flow<List<Category>> = throw NoCacheFoundException()

    fun cacheCategoryList(categories: List<Category>): Flow<List<Category>> = flowOf(categories)

}