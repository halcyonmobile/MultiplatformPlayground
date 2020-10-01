package com.halcyonmobile.multiplatformplayground.shared.util

import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.api.db.DatabaseFactory
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.usecase.FetchCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel

// TODO create a proper solution
class ServiceLocator {

    fun getDatabase() = DatabaseFactory.getInstance().create()

    // 🤢🤮
    fun getHomeViewModel(): HomeViewModel =
        CategoryRepository(
            CategoryRemoteSource(CategoryApi()),
            CategoryLocalSource(getDatabase())
        ).let {
            HomeViewModel(GetCategoriesUseCase(it), FetchCategoriesUseCase(it))
        }
}