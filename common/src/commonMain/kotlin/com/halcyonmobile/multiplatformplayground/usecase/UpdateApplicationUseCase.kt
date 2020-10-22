package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class UpdateApplicationUseCase(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(application: Application) = Result {
        applicationRepository.update(application)
    }
}