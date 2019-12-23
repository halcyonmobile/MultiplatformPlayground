package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.util.File

class CreateApplicationUseCase internal constructor(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(
        applicationWithDetail: ApplicationWithDetail,
        icon: File,
        screenshot: List<File>
    ) = applicationRepository.create(applicationWithDetail, icon, screenshot)
}