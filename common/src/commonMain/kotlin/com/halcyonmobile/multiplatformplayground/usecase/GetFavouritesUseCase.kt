package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository

class GetFavouritesUseCase internal constructor(private val applicationRepository: ApplicationRepository) {

    // TODO map to wrapped resource
    operator fun invoke() = applicationRepository.favouritesStream
}