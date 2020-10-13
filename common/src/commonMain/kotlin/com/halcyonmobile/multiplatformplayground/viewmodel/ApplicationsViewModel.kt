package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.ApplicationUiModel
import com.halcyonmobile.multiplatformplayground.model.toApplicationUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.GetApplicationsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationsViewModel internal constructor(
    private val categoryId: Long,
    private val getApplications: GetApplicationsUseCase
) : CoroutineViewModel() {

    private val _applications =
        MutableStateFlow<List<ApplicationUiModel>>(listOf(ApplicationUiModel.Loading))
    val applications: StateFlow<List<ApplicationUiModel>> = _applications

    private var pageOffset = 0

    init {
        load()
    }

    fun load() {
        coroutineScope.launch {
            setLoading(true)
            when (val result = getApplications(categoryId, pageOffset, PER_PAGE)) {
                is Result.Success -> {
                    _applications.value = result.value.map { it.toApplicationUiModel() }
                    pageOffset++
                }
                is Result.Error -> {
                    // TODO handle error
                }
            }
            setLoading(false)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _applications.value = if (isLoading) {
            _applications.value + ApplicationUiModel.Loading
        } else {
            _applications.value - ApplicationUiModel.Loading
        }
    }

    companion object {
        const val PER_PAGE = 10
    }
}