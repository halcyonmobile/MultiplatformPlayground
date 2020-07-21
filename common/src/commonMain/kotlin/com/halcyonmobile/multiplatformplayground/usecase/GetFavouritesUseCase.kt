package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository

internal class GetFavouritesUseCase(private val applicationRepository: ApplicationRepository) {

    operator fun invoke() = applicationRepository.favouritesStream
}