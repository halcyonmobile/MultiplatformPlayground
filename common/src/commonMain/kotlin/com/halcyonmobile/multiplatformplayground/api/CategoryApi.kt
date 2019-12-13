package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.shared.util.PAGE
import com.halcyonmobile.multiplatformplayground.shared.util.PER_PAGE
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class CategoryApi : KtorApi() {

    suspend fun getCategories(page: Int, perPage: Int): List<Category> = client.get {
        apiUrl("categories")
        parameter(PAGE, page)
        parameter(PER_PAGE, perPage)
    }
}