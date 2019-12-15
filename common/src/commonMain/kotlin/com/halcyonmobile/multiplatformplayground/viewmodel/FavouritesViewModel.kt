package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.observer.Observable
import com.halcyonmobile.multiplatformplayground.usecase.GetFavouritesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavouritesViewModel internal constructor(private val getFavourites: GetFavouritesUseCase) :
    CoroutineViewModel() {

    // todo handle state
    val favourites = Observable<List<Application>>()

    init {
        coroutineScope.launch {
            getFavourites()
                .collect {
                    favourites.value = it
                }
        }
    }
}