package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.*
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.shared.util.File
import com.halcyonmobile.multiplatformplayground.shared.util.log
import com.halcyonmobile.multiplatformplayground.usecase.CreateApplicationUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

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

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading


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

    override fun onNameChanged(name: String) {
        _uploadApplicationUiModel.value = _uploadApplicationUiModel.value.copy(name = name)
    }

    override fun onDeveloperChanged(developer: String) {
        _uploadApplicationUiModel.value =
            _uploadApplicationUiModel.value.copy(developer = developer)
    }

    override fun onIconChanged(icon: File) {
        _uploadApplicationUiModel.value = _uploadApplicationUiModel.value.copy(icon = icon)
    }

    override fun onRatingChanged(rating: String) {
        _uploadApplicationUiModel.value =
            _uploadApplicationUiModel.value.copy(rating = rating.toFloat())
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

    // TODO handle screenshots

    fun submit() {
        // TODO validate
        coroutineScope.launch {
            when (val result =
                createApplication(uploadApplicationUiModel.value.toUploadApplicationModel())) {
                is Result.Success -> {
                    // TODO handle success
                }
                is Result.Error -> {
                    // TODO handle error
                }
            }
        }
    }
}