package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationDetailUseCase
import com.halcyonmobile.multiplatformplayground.usecase.UpdateFavouriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationDetailViewModel internal constructor(
    applicationId: Long,
    private val getApplicationDetail: GetApplicationDetailUseCase,
    private val updateFavourite: UpdateFavouriteUseCase
) : CoroutineViewModel() {

    private val _applicationDetailUiModel = MutableStateFlow<ApplicationDetailUiModel?>(null)
    val applicationDetailUiModel: StateFlow<ApplicationDetailUiModel?> = _applicationDetailUiModel

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        coroutineScope.launch {
            when (val result = getApplicationDetail(applicationId)) {
                is Result.Success -> _applicationDetailUiModel.value =
                    result.value.toApplicationDetailUiModel()
                is Result.Error -> _error.value = result.exception.message
            }
        }
    }

    fun setFavourite() {
        applicationDetailUiModel.value?.let {
            coroutineScope.launch {
                when (val result = updateFavourite(it.id, it.favourite)) {
                    is Result.Error -> _error.value = result.exception.message
                }
            }
        }
    }
}