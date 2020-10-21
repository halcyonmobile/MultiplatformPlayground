package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class GetCategoryUseCase(private val repository: CategoryRepository) {

    suspend operator fun invoke(id: Long) = Result {
        repository.get(id) ?: throw IllegalArgumentException("No category with id: $id")
    }
}