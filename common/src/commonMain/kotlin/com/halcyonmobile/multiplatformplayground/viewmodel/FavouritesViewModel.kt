package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toApplicationUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetFavouritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class FavouritesViewModel :
    CoroutineViewModel(), KoinComponent {

    private val getFavourites: GetFavouritesUseCase by inject()

    private val _favourites = MutableStateFlow(emptyList<ApplicationUiModel.App>())
    private val _state = MutableStateFlow(State.LOADING)

    /**
     * Represents the favorite items
     */
    val favourites: StateFlow<List<ApplicationUiModel.App>> = _favourites

    /**
     * Represents the current state of the view
     */
    val state: StateFlow<State>
        get() = _state

    /**
     * Convenience method for iOS observing the [favourites]
     */
    @Suppress("unused")
    fun observeFavourites(onChange: (List<ApplicationUiModel.App>) -> Unit) {
        favourites.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    /**
     * Convenience method for iOS observing the [state]
     */
    @Suppress("unused")
    fun observeState(onChange: (State) -> Unit) {
        state.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    init {
        loadFavourites()
    }

    /**
     * Load the favourite applications
     */
    fun loadFavourites() {
        _state.value = State.LOADING

        coroutineScope.launch {
            _state.value = when (val result = getFavourites()) {
                is Result.Success -> {
                    _favourites.value =
                        result.value.mapNotNull { it.toApplicationUiModel() as? ApplicationUiModel.App }
                    State.NORMAL
                }
                is Result.Error -> State.ERROR
            }
        }
    }

    enum class State {
        LOADING,
        NORMAL,
        ERROR
    }
}