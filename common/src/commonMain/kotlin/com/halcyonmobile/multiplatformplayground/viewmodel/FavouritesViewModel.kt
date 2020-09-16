package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.usecase.GetFavouritesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModel internal constructor(private val getFavourites: GetFavouritesUseCase) :
    CoroutineViewModel() {

    private val _favourites = MutableStateFlow(emptyList<Application>())
    val favourites: StateFlow<List<Application>> = _favourites

    init {
        coroutineScope.launch {
            getFavourites()
                .collect {
                    _favourites.value = it
                }
        }
    }
}