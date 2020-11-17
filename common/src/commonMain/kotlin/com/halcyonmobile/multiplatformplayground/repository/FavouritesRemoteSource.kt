package com.halcyonmobile.multiplatformplayground.repository

import com.halcyonmobile.multiplatformplayground.api.FavouritesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class FavouritesRemoteSource(private val api: FavouritesApi) {

    suspend fun get() = withContext(Dispatchers.Default) {
        api.getFavourites()
    }
}