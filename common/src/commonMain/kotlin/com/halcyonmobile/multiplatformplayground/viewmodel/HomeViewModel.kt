package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.MR
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.ui.CategoryTabUiModel
import com.halcyonmobile.multiplatformplayground.shared.CoroutineViewModel
import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.FetchCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import kotlinx.coroutines.launch
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import com.halcyonmobile.multiplatformplayground.model.ui.toCategoryTabUiModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : CoroutineViewModel(), KoinComponent {

    private val getCategories: GetCategoriesUseCase by inject()
    private val fetchCategories: FetchCategoriesUseCase by inject()

    private val _categories = MutableStateFlow(emptyList<Category>())
    private val selectedTabIndex = MutableStateFlow(0)
    val categoryTabs = _categories.combine(selectedTabIndex) { categories, selectedTabIndex ->
        categories.mapIndexed { index, category ->
            category.toCategoryTabUiModel(index == selectedTabIndex)
        }
    }
    val selectedCategory =
        categoryTabs.map { categoryTabs -> categoryTabs.firstOrNull { it.isSelected } }

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State>
        get() = _state

    val title = StringDesc.Resource(MR.strings.home)

    init {
        getData(getCategories::invoke)
    }

    private fun getData(operation: suspend () -> Result<List<Category>>) {
        coroutineScope.launch {
            _state.value = State.LOADING

            _state.value = when (val result = operation()) {
                is Result.Success -> {
                    _categories.value = result.value
                    State.NORMAL
                }
                is Result.Error -> State.ERROR
            }
        }
    }

    /**
     * Convenience method for iOS observing the [categoryTabs]
     */
    @Suppress("unused")
    fun observeCategories(onChange: (List<CategoryTabUiModel>) -> Unit) {
        categoryTabs.onEach {
            onChange(it)
        }.launchIn(coroutineScope)
    }

    fun fetch() {
        getData(fetchCategories::invoke)
    }

    fun onTabClicked(index: Int) {
        selectedTabIndex.value = index
    }

    enum class State {
        LOADING,
        NORMAL,
        ERROR
    }
}