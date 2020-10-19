package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.CategoryUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toCategoryUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.CreateApplicationUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoryUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class UploadApplicationViewModel internal constructor(
    initialCategoryId: Long,
    private val getCategory: GetCategoryUseCase,
    private val createApplication: CreateApplicationUseCase
) : CoroutineViewModel() {

    private val selectedCategoryId = MutableStateFlow(initialCategoryId)
    private val _category = MutableStateFlow<CategoryUiModel?>(null)
    val category: StateFlow<CategoryUiModel?>
        get() = _category

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
        }.launchIn(coroutineScope)
    }

    // TODO implement submit
}