package com.halcyonmobile.multiplatformplayground.repository

import com.halcyonmobile.multiplatformplayground.api.FavouritesApi
import com.halcyonmobile.multiplatformplayground.shared.util.DispatcherProvider
import kotlinx.coroutines.withContext

internal class FavouritesRemoteSource(private val api: FavouritesApi, private val dispatcherProvider: DispatcherProvider) {

    suspend fun get() = withContext(dispatcherProvider.io) {
        api.getFavourites()
    }
}