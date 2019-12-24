package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.observer.Observable
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel internal constructor(
    private val getCategories: GetCategoriesUseCase,
    private val categoryRepository: CategoryRepository
) :
    CoroutineViewModel() {

    val categories = Observable<List<Category>>()

    init {
        coroutineScope.launch {
            categoryRepository.stream
                .collect {
                    categories.value = it
                    println(it)
                }
        }
        coroutineScope.launch {
            try {
                getCategories()
            } catch (e: Exception) {
                // todo handle error
            }
        }
    }
}