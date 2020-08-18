package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.shared.util.log
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.shared.observer.Observable
import com.halcyonmobile.multiplatformplayground.usecase.FetchCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel internal constructor(
    private val getCategories: GetCategoriesUseCase,
    private val fetchCategories: FetchCategoriesUseCase,
    private val categoryRepository: CategoryRepository
) : CoroutineViewModel() {

    val categories = Observable<List<Category>>()
    val error = Observable<String>()

    init {
        coroutineScope.launch {
            categoryRepository.categories.collect {
                categories.value = it
                log("Categories stream: $it")
            }
        }
        coroutineScope.launch {
            when (val result = getCategories()) {
                is Result.Error -> error.value = result.exception.message
            }
        }
    }

    fun fetch() = coroutineScope.launch {
        when (val result = fetchCategories()) {
            is Result.Error -> error.value = result.exception.message
        }
    }
}