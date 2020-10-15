package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
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
    private val getApplicationDetail: GetApplicationDetailUseCase,
    private val updateFavourite: UpdateFavouriteUseCase,
    applicationId: Long
) : CoroutineViewModel() {

    private val _applicationWithDetail = MutableStateFlow<ApplicationWithDetail?>(null)
    val applicationWithDetail: StateFlow<ApplicationWithDetail?> = _applicationWithDetail

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        coroutineScope.launch {
            when (val result = getApplicationDetail(applicationId)) {
                is Result.Success -> _applicationWithDetail.value = result.value
                is Result.Error -> _error.value = result.exception.message
            }
        }
    }

    fun setFavourite() {
        applicationWithDetail.value?.application?.let {
            coroutineScope.launch {
                when (val result = updateFavourite(it.id, it.favourite)) {
                    is Result.Error -> _error.value = result.exception.message
                }
            }
        }
    }
}