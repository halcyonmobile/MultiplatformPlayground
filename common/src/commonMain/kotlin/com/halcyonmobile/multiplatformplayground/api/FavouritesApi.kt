package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.model.Application
import io.ktor.client.request.*

internal class FavouritesApi : KtorApi() {

    suspend fun getFavourites(): List<Application> = client.get {
        apiUrl("favourites")
    }
}