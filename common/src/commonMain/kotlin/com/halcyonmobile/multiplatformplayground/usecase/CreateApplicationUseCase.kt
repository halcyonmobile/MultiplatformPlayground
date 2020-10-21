package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.UploadApplicationModel
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class CreateApplicationUseCase (private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(
        uploadApplicationModel: UploadApplicationModel
    ) = Result {
        applicationRepository.create(uploadApplicationModel)
    }
}