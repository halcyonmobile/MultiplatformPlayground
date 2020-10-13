package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class GetApplicationsUseCase(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(categoryId: Long, page: Int, perPage: Int) = Result {
        applicationRepository.getByCategory(categoryId, page, perPage)
    }
}