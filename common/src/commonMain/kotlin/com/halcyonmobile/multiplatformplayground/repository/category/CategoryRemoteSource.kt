package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.shared.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CategoryRemoteSource(private val categoryApi: CategoryApi) {

    // TODO add thread switching
    suspend fun get(page: Int, perPage: Int) = categoryApi.getCategories(page, perPage)

}