package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.util.extension.merge
import kotlinx.coroutines.flow.*

class GetApplicationUseCase internal constructor(private val applicationRepository: ApplicationRepository) {

    // todo wrap to resource
    operator fun invoke(id: Long) =
        applicationRepository.getDetailById(id)

}
