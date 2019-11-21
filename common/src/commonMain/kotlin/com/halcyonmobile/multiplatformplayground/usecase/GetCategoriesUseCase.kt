package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository

class GetCategoriesUseCase internal constructor(private val categoryRepository: CategoryRepository) {

    // todo map to Result wrapper
    suspend operator fun invoke() = categoryRepository.get()

}