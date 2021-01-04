package com.halcyonmobile.multiplatformplayground.repository

import com.halcyonmobile.multiplatformplayground.repository.category.CategoryLocalSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRemoteSource
import com.halcyonmobile.multiplatformplayground.repository.category.CategoryRepository
import com.halcyonmobile.multiplatformplayground.util.BaseTest
import com.halcyonmobile.multiplatformplayground.util.category
import com.halcyonmobile.multiplatformplayground.util.category2
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CategoryRepositoryTest : BaseTest() {

    private lateinit var sut: CategoryRepository

    @RelaxedMockK
    private lateinit var remoteSource: CategoryRemoteSource

    @RelaxedMockK
    private lateinit var localSource: CategoryLocalSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = CategoryRepository(remoteSource, localSource)
        coEvery { remoteSource.get(any(), any()) } returns listOf(category, category2)
    }

    @Test
    fun `Given empty cache When categories are requested Then a remote fetch operation is called`() =
        coroutineTestRule.runTest {
            coEvery { localSource.getCategories() } returns emptyList()

            sut.get()
            coVerify { remoteSource.get(any(), any()) }
        }

    @Test
    fun `Given empty cache When categories are requested Then the remote categories are returned`() =
        coroutineTestRule.runTest {
            val remoteCategories = listOf(category)
            coEvery { localSource.getCategories() } returns emptyList()
            coEvery { remoteSource.get(any(), any()) } returns remoteCategories

            assertEquals(expected = remoteCategories, actual = sut.get())
        }

    @Test
    fun `Given cached categories When get is called Then remote operation is not called`() =
        coroutineTestRule.runTest {
            coEvery { localSource.getCategories() } returns listOf(category)

            sut.get()
            coVerify(exactly = 0) { remoteSource.get(any(), any()) }
        }

    @Test
    fun `Given cached categories When get is called Then the cached categories are returned`() =
        coroutineTestRule.runTest {
            val localCategories = listOf(category)
            coEvery { localSource.getCategories() } returns localCategories

            assertEquals(expected = localCategories, sut.get())
        }

    @Test
    fun `Given cached categories When fetch is called Then remote operation is called`() =
        coroutineTestRule.runTest {
            coEvery { localSource.getCategories() } returns listOf(category)

            sut.fetch()
            coVerify { remoteSource.get(any(), any()) }
        }

    @Test
    fun `Given cached categories When fetch is called Then remote categories are returned`() =
        coroutineTestRule.runTest {
            val remoteCategories = listOf(category2)
            coEvery { localSource.getCategories() } returns listOf(category)
            coEvery { remoteSource.get(any(), any()) } returns remoteCategories

            assertEquals(expected = remoteCategories, actual = sut.fetch())
        }

    @Test
    fun `Given cached category When that category is requested Then the category is returned`() =
        coroutineTestRule.runTest {
            val cachedCategory = category
            coEvery { localSource.get(any()) } returns category

            assertEquals(expected = cachedCategory, actual = sut.get(category.id))
        }

    @Test
    fun `Given not cached category When that category is requested Then null is returned`() =
        coroutineTestRule.runTest {
            coEvery { localSource.get(any()) } returns null

            assertEquals(expected = null, actual = sut.get(category.id))
        }

    @Test
    fun `When get categories is called Then the results is cached`() = coroutineTestRule.runTest {
        sut.get()
        verify { localSource.insert(any()) }
    }

    @Test
    fun `When fetch categories is called Then the result is cached`() = coroutineTestRule.runTest {
        sut.fetch()
        verify { localSource.insert(any()) }
    }
}