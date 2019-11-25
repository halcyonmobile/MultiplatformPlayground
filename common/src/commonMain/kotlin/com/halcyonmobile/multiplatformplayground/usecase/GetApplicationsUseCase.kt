package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository

class GetApplicationsUseCase internal constructor(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(categoryId: Long, page: Int, perPage: Int) =
        applicationRepository.getByCategory(categoryId, page, perPage)
}