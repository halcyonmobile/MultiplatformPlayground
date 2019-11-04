package com.nagyrobi.multiplatformplayground.repository.category

import com.nagyrobi.multiplatformplayground.model.Category
import kotlinx.coroutines.flow.Flow

internal expect class CategoryLocalSource {
    fun getCategories(): Flow<List<Category>>

    fun cacheCategoryList(): Flow<List<Category>>
}