package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.observer.Observable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO rewrite repositories to use cases
class HomeViewModel internal constructor(private val categoryRepository: CategoryRepository) :
    CoroutineViewModel() {

    val categories = Observable<List<Category>>()

    init {
        launch {
            // todo move to use case and handle resource success or error
            categoryRepository.get().collect {
                categories.value = it
            }
        }
    }
}