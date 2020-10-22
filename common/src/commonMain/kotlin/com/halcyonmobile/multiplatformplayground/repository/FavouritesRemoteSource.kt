package com.halcyonmobile.multiplatformplayground.repository

import com.halcyonmobile.multiplatformplayground.api.FavouritesApi

internal class FavouritesRemoteSource(private val api: FavouritesApi) {

    suspend fun get() = api.getFavourites()
}