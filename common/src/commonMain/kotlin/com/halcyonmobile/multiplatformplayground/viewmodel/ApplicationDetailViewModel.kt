package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ui.ApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.model.ui.toApplication
import com.halcyonmobile.multiplatformplayground.model.ui.toApplicationDetailUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationDetailUseCase
import com.halcyonmobile.multiplatformplayground.usecase.UpdateApplicationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationDetailViewModel internal constructor(
    applicationId: Long,
    private val getApplicationDetail: GetApplicationDetailUseCase,
    private val updateApplication: UpdateApplicationUseCase
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

    fun updateFavourite() {
        val applicationDetailUiModel = _applicationDetailUiModel.value ?: return
        _applicationDetailUiModel.value =
            applicationDetailUiModel.copy(favourite = !applicationDetailUiModel.favourite)

        coroutineScope.launch {
            when (val result = updateApplication(
                applicationDetailUiModel.toApplication()
                    .copy(favourite = !applicationDetailUiModel.favourite)
            )) {
                is Result.Error -> {
                    // reset the favourite state
                    _error.value = result.exception.message
                    _applicationDetailUiModel.value = applicationDetailUiModel
                }
            }
        }
    }
}