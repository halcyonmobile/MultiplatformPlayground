package com.halcyonmobile.multiplatformplayground.repository.category

import com.halcyonmobile.multiplatformplayground.db.MultiplatformDatabase
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.shared.util.log

val categoryMapper: (id: Long, name: String, icon: String) -> Category =
    { id, name, icon -> Category(id, name, icon) }

class CategoryLocalSource internal constructor(database: MultiplatformDatabase) {
    private val queries = database.categoriesQueries

    // TODO remove showcase log
    fun getCategories() = queries.selectAll(categoryMapper).executeAsList().also {
        log("Categories from localsource: $it")
    }

    fun get(id: Long) = queries.getById(id, categoryMapper).executeAsOneOrNull()

    fun insert(category: Category) = queries.insert(
        id = if (category.id == 0L) null else category.id,
        name = category.name,
        icon = category.icon
    )

    fun clearCache() = queries.clear()
}