package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ApplicationWithDetail
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationUseCase
import com.halcyonmobile.multiplatformplayground.usecase.UpdateFavouriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationDetailViewModel internal constructor(
    private val getApplication: GetApplicationUseCase,
    private val updateFavourite: UpdateFavouriteUseCase,
    applicationId: Long
) : CoroutineViewModel() {

    private val _applicationWithDetail = MutableStateFlow<ApplicationWithDetail?>(null)
    val applicationWithDetail: StateFlow<ApplicationWithDetail?> = _applicationWithDetail

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        coroutineScope.launch {
            getApplication(applicationId).catch {
                // todo handle error
            }.collect {
                _applicationWithDetail.value = it
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