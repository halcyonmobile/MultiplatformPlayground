package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class GetCategoriesUseCase(private val repository: CategoryRepository) {

    suspend operator fun invoke() = Result {
        repository.get()
    }
}