package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.model.Category
import kotlinx.coroutines.flow.Flow

internal expect class CategoryLocalSource() {
    fun getCategories(): Flow<List<Category>>

    fun cacheCategoryList(): Flow<List<Category>>

}