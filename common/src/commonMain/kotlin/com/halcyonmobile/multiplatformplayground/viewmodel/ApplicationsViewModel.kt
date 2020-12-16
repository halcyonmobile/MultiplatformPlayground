package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toApplicationUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.shared.util.log
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class ApplicationsViewModel(
    private val categoryId: Long,
) : CoroutineViewModel(), KoinComponent {

    private val getApplications: GetApplicationsUseCase by inject()

    private val _items = MutableStateFlow<List<ApplicationUiModel>>(emptyList())
    private val _state = MutableStateFlow(State.NORMAL)
    private val _event = MutableSharedFlow<Event>()

    private val isLoading get() = items.value.contains(ApplicationUiModel.Loading)
    var isLastPage = false
    /**
     * Represents all the UI items
     */
    val items: StateFlow<List<ApplicationUiModel>> = _items

    /**
     * Represents the current state of the view
     */
    val state: StateFlow<State>
        get() = _state

    /**
     * Emits the possible events, that can happen during data retrieval
     */
    val event: SharedFlow<Event>
        get() = _event

    private var pageOffset = 0

    init {
        load()
    }

    /**
     * Load the next page of items
     */
    fun load() {
        if (isLoading || isLastPage) return

        coroutineScope.launch {
            setLoading(true)
            val result = getApplications(categoryId, pageOffset, PER_PAGE)
            setLoading(false)
            _state.value = when (result) {
                is Result.Success -> {
                    _items.value = _items.value + result.value.map { it.toApplicationUiModel() }
                    pageOffset++
                    if (result.value.size < PER_PAGE) {
                        isLastPage = true
                    }
                    if (_items.value.isEmpty()) State.EMPTY else State.NORMAL
                }
                is Result.Error -> {
                    _event.emit(Event.ERROR)
                    if (_items.value.isEmpty()) State.ERROR else State.NORMAL
                }
            }
            setLoading(false)
        }
    }

    /**
     * Refresh the current list of items
     */
    fun refresh() {
        pageOffset = 0
        _items.value = emptyList()
        load()
    }

    /**
     * Convenience method for iOS observing the [items]
     */
    @Suppress("unused")
    fun observeItems(onChange: (List<ApplicationUiModel>) -> Unit) {
        items.onEach {
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

    /**
     * Convenience method for iOS observing the [event]
     */
    @Suppress("unused")
    fun observeEvent(onChange: (Event) -> Unit) {
        event.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    private fun setLoading(isLoading: Boolean) {
        _items.value = if (isLoading) {
            _items.value + ApplicationUiModel.Loading
        } else {
            _items.value.filterIsInstance<ApplicationUiModel.App>()
        }
    }

    companion object {
        const val PER_PAGE = 10
    }

    enum class State {
        EMPTY,
        ERROR,
        NORMAL
    }

    enum class Event {
        ERROR
    }
}