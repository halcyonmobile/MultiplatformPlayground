package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Category
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class CategoryApi : KtorApi() {

    suspend fun getCategories(page: Int, perPage: Int): List<Category> = client.get {
        apiUrl("categories")
        parameter("page", page)
        parameter("perPage", perPage)
    }
}