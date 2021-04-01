package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.db.Categories
import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.halcyonmobile.multiplatformplayground.model.Category

val categoryMapper: (Categories) -> Category =
    { Category(it.id, it.name, it.icon) }

class CategoryLocalSource internal constructor(database: MultiplatformDatabase) {
    private val queries = database.categoriesQueries

    fun getCategories() = queries.selectAll().executeAsList().map(categoryMapper)

    fun get(id: Long) = queries.getById(id).executeAsOneOrNull()?.let(categoryMapper)

    fun insert(category: Category) = queries.insert(
        id = if (category.id == 0L) null else category.id,
        name = category.name,
        icon = category.icon
    )

    fun clearCache() = queries.clear()
}