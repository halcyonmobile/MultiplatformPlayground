package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.api.CategoryApi

internal class CategoryRemoteSource(private val categoryApi: CategoryApi) {

    suspend fun get(page: Int, perPage: Int) = categoryApi.getCategories(page, perPage)
}