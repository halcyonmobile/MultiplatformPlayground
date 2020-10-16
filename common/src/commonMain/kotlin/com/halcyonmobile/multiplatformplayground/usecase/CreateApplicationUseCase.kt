package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.ApplicationDetail
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.shared.util.File

internal class CreateApplicationUseCase (private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(
        applicationDetail: ApplicationDetail,
        icon: File,
        screenshot: List<File>
    ) = Result {
        applicationRepository.create(applicationDetail, icon, screenshot)
    }
}