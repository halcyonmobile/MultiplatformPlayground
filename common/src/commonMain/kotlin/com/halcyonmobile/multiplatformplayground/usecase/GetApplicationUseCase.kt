package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class GetApplicationUseCase(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(id: Long) = Result {
        applicationRepository.getDetailById(id)
    }
}
