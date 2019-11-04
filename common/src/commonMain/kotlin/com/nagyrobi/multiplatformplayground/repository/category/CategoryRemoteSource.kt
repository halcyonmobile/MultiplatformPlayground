package com.nagyrobi.multiplatformplayground.repository.category

import com.nagyrobi.multiplatformplayground.api.CategoryApi

internal class CategoryRemoteSource(private val categoryApi: CategoryApi) {

    suspend fun get(page: Int, perPage: Int) = categoryApi.getCategories(page, perPage)
}