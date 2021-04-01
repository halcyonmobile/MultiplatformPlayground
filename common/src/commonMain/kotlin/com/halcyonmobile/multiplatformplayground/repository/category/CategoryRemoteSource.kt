package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.shared.util.DispatcherProvider
import kotlinx.coroutines.withContext

internal class CategoryRemoteSource(
    private val categoryApi: CategoryApi,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun get(page: Int, perPage: Int) = withContext(dispatcherProvider.io) {
        categoryApi.getCategories(page, perPage)
    }

}