package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import io.ktor.client.request.get

internal class FavouritesApi(private val ktorApi: KtorApi) : KtorApi by ktorApi {

    suspend fun getFavourites(): List<Application> = client.get {
        apiUrl("favourites")
    }
}