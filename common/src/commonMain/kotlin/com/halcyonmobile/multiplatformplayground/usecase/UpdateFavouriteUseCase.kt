package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class UpdateFavouriteUseCase(private val applicationRepository: ApplicationRepository) {

    suspend operator fun invoke(id: Long, isFavourite: Boolean) = Result {
        applicationRepository.updateFavourites(id, isFavourite)
    }
}