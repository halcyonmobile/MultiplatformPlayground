package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.CategoryUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.UploadApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.UploadApplicationUiModelChangeListener
import com.halcyonmobile.multiplatformplayground.model.ui.toCategoryUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toUploadApplicationModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.shared.util.ImageFile
import com.halcyonmobile.multiplatformplayground.usecase.CreateApplicationUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoryUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UploadApplicationViewModel(
    initialCategoryId: Long,
) : CoroutineViewModel(), KoinComponent, UploadApplicationUiModelChangeListener {

    private val getCategory: GetCategoryUseCase by inject()
    private val createApplication: CreateApplicationUseCase by inject()

    private val selectedCategoryId = MutableStateFlow(initialCategoryId)

    private val _category = MutableStateFlow<CategoryUiModel?>(null)
    val category: StateFlow<CategoryUiModel?>
        get() = _category

    private val _uploadApplicationUiModel =
        MutableStateFlow(UploadApplicationUiModel(categoryId = initialCategoryId))
    val uploadApplicationUiModel: StateFlow<UploadApplicationUiModel>
        get() = _uploadApplicationUiModel

    private val _state = MutableStateFlow(State.NORMAL)
    val state: StateFlow<State>
        get() = _state

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event>
        get() = _event

    init {
        selectedCategoryId.onEach {
            when (val result = getCategory(it)) {
                is Result.Success -> _category.value = result.value.toCategoryUiModel()
                is Result.Error -> {
                    // TODO handle error
                }
            }
            _uploadApplicationUiModel.value = _uploadApplicationUiModel.value.copy(categoryId = it)
        }.launchIn(coroutineScope)
    }

    /**
     * Convenience method for iOS observing the [uploadApplicationUiModel]
     */
    @Suppress("unused")
    fun observeApplication(onChange: (UploadApplicationUiModel) -> Unit) {
        uploadApplicationUiModel.onEach {
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
     * Convenience method for iOS observing [event]
     */
    @Suppress("unused")
    fun observeEvent(onChange: (Event) -> Unit) {
        event.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    override fun onNameChanged(name: String) {
        _uploadApplicationUiModel.value = _uploadApplicationUiModel.value.copy(name = name)
    }

    override fun onDeveloperChanged(developer: String) {
        _uploadApplicationUiModel.value =
            _uploadApplicationUiModel.value.copy(developer = developer)
    }

    override fun onIconChanged(icon: ImageFile) {
        _uploadApplicationUiModel.value = _uploadApplicationUiModel.value.copy(icon = icon)
    }

    override fun onRatingChanged(rating: String) {
        val newRating = rating.toFloatOrNull() ?: return
        _uploadApplicationUiModel.value =
            _uploadApplicationUiModel.value.copy(rating = newRating)
    }

    override fun onDescriptionChanged(description: String) {
        _uploadApplicationUiModel.value =
            _uploadApplicationUiModel.value.copy(description = description)
    }

    override fun onDownloadsChanged(downloads: String) {
        _uploadApplicationUiModel.value =
            _uploadApplicationUiModel.value.copy(downloads = downloads)
    }

    override fun onCategoryChanged(categoryId: Long) {
        selectedCategoryId.value = categoryId
    }

    override fun onAddScreenshot(screenshot: ImageFile) {
        val uploadApplicationUiModel = _uploadApplicationUiModel.value
        _uploadApplicationUiModel.value =
            uploadApplicationUiModel.copy(screenshots = uploadApplicationUiModel.screenshots + screenshot)
    }

    fun submit() {
        // TODO validate
        coroutineScope.launch {
            _state.value = State.LOADING
            _event.emit(
                when (createApplication(uploadApplicationUiModel.value.toUploadApplicationModel())) {
                    is Result.Success -> Event.SUCCESSFUL_UPLOAD
                    is Result.Error -> {
                        _state.value = State.NORMAL
                        Event.ERROR
                    }
                }
            )
        }
    }

    enum class State {
        NORMAL,
        LOADING
    }

    enum class Event {
        ERROR,
        SUCCESSFUL_UPLOAD
    }
}