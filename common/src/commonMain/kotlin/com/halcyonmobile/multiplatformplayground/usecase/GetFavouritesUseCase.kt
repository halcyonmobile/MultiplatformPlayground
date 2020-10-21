package com.halcyonmobile.multiplatformplayground.usecase

import com.halcyonmobile.multiplatformplayground.repository.FavouritesRemoteSource
import com.halcyonmobile.multiplatformplayground.shared.Result

internal class GetFavouritesUseCase(private val remoteSource: FavouritesRemoteSource) {

    suspend operator fun invoke() = Result {
        remoteSource.get()
    }
}