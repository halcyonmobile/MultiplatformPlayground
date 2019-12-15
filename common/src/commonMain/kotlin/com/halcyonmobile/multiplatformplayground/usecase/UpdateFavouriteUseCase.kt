package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository

class UpdateFavouriteUseCase internal constructor(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(id: Long, isFavourite: Boolean) =
        applicationRepository.updateFavourites(id, isFavourite)
}