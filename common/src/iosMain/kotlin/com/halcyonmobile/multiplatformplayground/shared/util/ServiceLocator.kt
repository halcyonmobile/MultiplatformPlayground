package com.halcyonmobile.multiplatformplayground.shared.util

import com.halcyonmobile.multiplatformplayground.api.CategoryApi
import com.halcyonmobile.multiplatformplayground.api.db.DatabaseFactory
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.usecase.FetchCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoryUseCase
import com.halcyonmobile.multiplatformplayground.usecase.CreateApplicationUseCase
import com.halcyonmobile.multiplatformplayground.viewmodel.HomeViewModel
import com.halcyonmobile.multiplatformplayground.viewmodel.UploadApplicationViewModel
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRemoteSource
import com.halcyonmobile.multiplatformplayground.api.ApplicationApi
// TODO create a proper solution
class ServiceLocator {

    private val database = DatabaseFactory.getInstance().create()
    private val categoryRepository = CategoryRepository(
        CategoryRemoteSource(CategoryApi()),
        CategoryLocalSource(database)
    )
    private val applicationRepository = ApplicationRepository(ApplicationRemoteSource(ApplicationApi()))

    // 🤢🤮
    fun getHomeViewModel(): HomeViewModel =
        HomeViewModel(
            GetCategoriesUseCase(categoryRepository),
            FetchCategoriesUseCase(categoryRepository)
        )

    fun getUploadApplicationViewModel(categoryId: Long): UploadApplicationViewModel = UploadApplicationViewModel(
        categoryId,
        GetCategoryUseCase(categoryRepository),
        CreateApplicationUseCase(applicationRepository)
    )
}