package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toApplication
import com.halcyonmobile.multiplatformplayground.model.ui.toApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationDetailUseCase
import com.halcyonmobile.multiplatformplayground.usecase.UpdateApplicationUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ApplicationDetailViewModel internal constructor(
    private val applicationId: Long,
    private val getApplicationDetail: GetApplicationDetailUseCase,
    private val updateApplication: UpdateApplicationUseCase
) : CoroutineViewModel() {

    private val _applicationDetailUiModel = MutableStateFlow<ApplicationDetailUiModel?>(null)
    private val _state = MutableStateFlow(State.LOADING)
    private val _event = MutableSharedFlow<Event>()

    /**
     * Represents the detail item
     */
    val applicationDetailUiModel: StateFlow<ApplicationDetailUiModel?> = _applicationDetailUiModel

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

    init {
        loadDetail()
    }

    /**
     * Load Detail data
     */
    fun loadDetail() {
        _state.value = State.LOADING
        coroutineScope.launch {
            _state.value = when (val result = getApplicationDetail(applicationId)) {
                is Result.Success -> {
                    _applicationDetailUiModel.value = result.value.toApplicationDetailUiModel()
                    State.NORMAL
                }
                is Result.Error -> State.ERROR
            }
        }
    }

    /**
     * Convenience method for iOS observing the [applicationDetailUiModel]
     */
    @Suppress("unused")
    fun observeApplicationDetail(onChange: (ApplicationDetailUiModel) -> Unit) {
        applicationDetailUiModel.onEach {
            if (it != null) {
                onChange(it)
            }
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

    fun updateFavourite() {
        val applicationDetailUiModel = _applicationDetailUiModel.value ?: return
        _applicationDetailUiModel.value =
            applicationDetailUiModel.copy(favourite = !applicationDetailUiModel.favourite)

        coroutineScope.launch {
            when (updateApplication(
                applicationDetailUiModel.toApplication()
                    .copy(favourite = !applicationDetailUiModel.favourite)
            )) {
                is Result.Success -> Unit
                is Result.Error -> {
                    // reset the favourite state
                    _applicationDetailUiModel.value = applicationDetailUiModel
                    _event.emit(Event.ERROR)
                }
            }
        }
    }

    enum class State {
        LOADING,
        NORMAL,
        ERROR
    }

    enum class Event {
        ERROR
    }
}