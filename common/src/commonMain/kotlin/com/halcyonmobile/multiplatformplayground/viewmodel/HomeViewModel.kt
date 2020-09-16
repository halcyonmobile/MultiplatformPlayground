package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.MR
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
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

class HomeViewModel internal constructor(
    private val getCategories: GetCategoriesUseCase,
    private val fetchCategories: FetchCategoriesUseCase,
    private val categoryRepository: CategoryRepository
) : CoroutineViewModel() {

    val categories = Observable<List<Category>>()
    val error = Observable<String>()
    val title = StringDesc.Resource(MR.strings.home)

    init {
        coroutineScope.launch {
            categoryRepository.categories.collect {
                log("Categories stream: $it")
                categories.value = it
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