package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.MR
import com.halcyonmobile.multiplatformplayground.shared.util.log
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.FetchCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel internal constructor(
    private val getCategories: GetCategoriesUseCase,
    private val fetchCategories: FetchCategoriesUseCase
) : CoroutineViewModel() {

    private val _categories = MutableStateFlow(emptyList<Category>())
    val categories: StateFlow<List<Category>> = _categories

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    val title = StringDesc.Resource(MR.strings.home)

    init {
        coroutineScope.launch {
            when (val result = getCategories()) {
                is Result.Success -> {
                    _categories.value = result.value
                    log("Categories: ${result.value}")
                }
                is Result.Error -> _error.value = result.exception.message
            }
        }
    }

    fun fetch() = coroutineScope.launch {
        when (val result = fetchCategories()) {
            is Result.Error -> _error.value = result.exception.message
        }
    }
}