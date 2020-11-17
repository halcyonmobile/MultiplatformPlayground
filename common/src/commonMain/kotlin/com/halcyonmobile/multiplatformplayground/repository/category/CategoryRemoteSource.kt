package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CategoryRemoteSource(private val categoryApi: CategoryApi) {

    suspend fun get(page: Int, perPage: Int) = withContext(Dispatchers.Default) {
        categoryApi.getCategories(page, perPage)
    }

}