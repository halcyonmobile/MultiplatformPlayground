package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.shared.util.File

internal class CreateApplicationUseCase (private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(
        applicationWithDetail: ApplicationWithDetail,
        icon: File,
        screenshot: List<File>
    ) = Result {
        applicationRepository.create(applicationWithDetail, icon, screenshot)
    }
}