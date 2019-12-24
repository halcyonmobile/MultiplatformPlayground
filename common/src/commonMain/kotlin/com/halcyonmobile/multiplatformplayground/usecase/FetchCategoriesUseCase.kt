package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository

class FetchCategoriesUseCase internal constructor(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke() = categoryRepository.fetch()

}