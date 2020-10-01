package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository

internal class GetApplicationsUseCase(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(categoryId: Long, page: Int, perPage: Int) =
        applicationRepository.getByCategory(categoryId, page, perPage)
}