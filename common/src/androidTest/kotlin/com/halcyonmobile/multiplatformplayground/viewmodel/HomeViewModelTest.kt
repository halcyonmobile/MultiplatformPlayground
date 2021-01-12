package com.halcyonmobile.multiplatformplayground.viewmodel

import com.halcyonmobile.multiplatformplayground.shared.Result
import com.halcyonmobile.multiplatformplayground.usecase.FetchCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.usecase.GetCategoriesUseCase
import com.halcyonmobile.multiplatformplayground.util.*
import io.mockk.coEvery
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Test
import org.koin.test.mock.declareMock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HomeViewModelTest : KoinBaseTest() {

    private lateinit var sut: HomeViewModel

    private lateinit var getCategories: GetCategoriesUseCase
    private lateinit var fetchCategories: FetchCategoriesUseCase

    @Before
    fun setup() {
        getCategories = koinTestRule.koin.declareMock {
            coEvery { this@declareMock() } returns Result.Success(listOf(category))
        }
        fetchCategories = koinTestRule.koin.declareMock {
            coEvery { this@declareMock() } returns Result.Success(listOf(category, category2))
        }

        sut = HomeViewModel()
    }

    @Test
    fun `When categories can't be retrieved Then State is set to error`() =
        coroutineTestRule.runTest {
            coEvery { fetchCategories() } returns Result.Error(RuntimeException())

            sut.fetch()

            assertEquals(expected = HomeViewModel.State.ERROR, actual = sut.state.first())
        }

    @Test
    fun `When categories are retrieved Then state is set to normal`() = coroutineTestRule.runTest {
        assertEquals(expected = HomeViewModel.State.NORMAL, actual = sut.state.first())
        assertEquals(expected = listOf(categoryTabUiModel), actual = sut.categoryTabs.first())
    }

    @Test
    fun `When categories are retrieved Then the first category is selected`() =
        coroutineTestRule.runTest {
            assertEquals(
                expected = sut.categoryTabs.first().first(),
                actual = sut.selectedCategory.first()
            )
        }

    @Test
    fun `When a new category is selected Then the list of category tabs are updated with this change`() =
        coroutineTestRule.runTest {
            sut.fetch()
            val expectedCategory = sut.categoryTabs.first()[1]
            sut.onTabClicked(1)

            assertEquals(
                expected = sut.categoryTabs.first()[1],
                actual = sut.selectedCategory.first()
            )
            val categoryTabs = sut.categoryTabs.first()
            assertTrue {
                categoryTabs.all {
                    if (it.id == expectedCategory.id) {
                        it.isSelected
                    } else {
                        !it.isSelected
                    }
                }
            }
        }

    @Test
    fun `Given outdated categories When a fetch is requested Then the fresh categories are retrieved`() = coroutineTestRule.runTest {
        sut.fetch()
        assertEquals(
            expected = listOf(categoryTabUiModel, categoryTabUiModel2),
            actual = sut.categoryTabs.first()
        )
    }
}