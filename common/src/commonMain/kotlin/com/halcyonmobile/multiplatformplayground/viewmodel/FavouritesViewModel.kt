package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.repository.application.ApplicationRepository
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.observer.Observable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavouritesViewModel internal constructor(private val applicationRepository: ApplicationRepository) :
    CoroutineViewModel() {

    // todo handle state
    val favourites = Observable<List<Application>>()

    init {
        coroutineScope.launch {
            applicationRepository.favouritesStream.collect {
                favourites.value = it
                // todo update state
            }
        }
    }
}