package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toApplicationUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetFavouritesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModel internal constructor(private val getFavourites: GetFavouritesUseCase) :
    CoroutineViewModel() {

    private val _favourites = MutableStateFlow(emptyList<ApplicationUiModel>())
    val favourites: StateFlow<List<ApplicationUiModel>> = _favourites

    /**
     * Convenience method for iOS observing the [favourites]
     */
    @Suppress("unused")
    fun observeFavourites(onChange: (List<ApplicationUiModel>) -> Unit) {
        favourites.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    init {
        coroutineScope.launch {
            when (val result = getFavourites()) {
                is Result.Success -> _favourites.value =
                    result.value.map { it.toApplicationUiModel() }
                is Result.Error -> {
                    // TODO error handling
                }
            }
        }
    }
}