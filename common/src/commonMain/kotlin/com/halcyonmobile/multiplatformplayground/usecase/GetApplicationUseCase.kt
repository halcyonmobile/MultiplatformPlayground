package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.util.extension.merge
import kotlinx.coroutines.flow.*

internal class GetApplicationUseCase(private val applicationRepository: ApplicationRepository) {

    operator fun invoke(id: Long) =
        applicationRepository.getDetailById(id)

}
