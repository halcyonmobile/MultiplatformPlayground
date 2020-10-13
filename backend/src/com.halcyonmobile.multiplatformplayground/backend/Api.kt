package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.util.InternalAPI

// todo general error handling
internal fun Routing.api(localSource: LocalSource) {
    apiApplications(localSource)
    apiCreateApplication(localSource)
    apiUpdateApplication(localSource)
    apiGetApplication(localSource)
    apiFilterApplications(localSource)
    apiGetCategories(localSource)
    apiPostCategory(localSource)
}

/**
 * GET /api/v1/applications
 */
private fun Routing.apiApplications(localSource: LocalSource) {
    get("/applications") {
        localSource.getApplications().let {
            call.respond(it)
        }
    }
}

/**
 *  POST /api/v1/applications
 */
// todo handle lists
@UseExperimental(InternalAPI::class)
private fun Routing.apiCreateApplication(localSource: LocalSource) {
    post("/applications") {
        val applicationRequest = call.receive<ApplicationRequest>()

        val appId = localSource.getNextApplicationId()
        try {
            val screenshots = applicationRequest.screenshots.map {
                val savedScreenshot = it.copy(name = it.name, image = it.image)
                val id = localSource.saveScreenshot(savedScreenshot, appId)
                savedScreenshot.copy(id = id)
            }
            val category = localSource.getCategory(applicationRequest.categoryId)

            localSource.saveApplication(applicationRequest)

            call.respond(HttpStatusCode.Created)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict)
        }
    }
}


/**
 * PUT /api/v1/applications
 */
private fun Routing.apiUpdateApplication(localSource: LocalSource) {
    put("/applications") {
        call.receive<Application>().let {
            try {
                localSource.updateApplication(it)
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict)
            }
        }
    }
}

/**
 * GET /api/v1/applications/:id
 */
private fun Routing.apiGetApplication(localSource: LocalSource) {
    get("/applications/{id}") {
        val id = call.parameters["id"].toString().toLong()
        localSource.getApplication(id).let {
            call.respond(it)
        }
        // return application
    }
}

/**
 * GET /api/v1/applications/filter
 */
private fun Routing.apiFilterApplications(localSource: LocalSource) {
    get("/applications/filter") {
        val name = call.request.queryParameters[NAME_QUERY_KEY].toString()
        val categoryId = call.request.queryParameters[CATEGORY_QUERY_KEY]!!.toLong()

        localSource.getApplications(name, categoryId).let {
            call.respond(it)
        }
    }
}

/**
 * GET /api/v1/categories
 */
private fun Routing.apiGetCategories(localSource: LocalSource) {
    get("/categories") {
        localSource.getCategories().let {
            call.respond(it)
        }
    }
}

/**
 * POST /api/v1/categories
 */
@UseExperimental(InternalAPI::class)
private fun Routing.apiPostCategory(localSource: LocalSource) {
    post("/category") {
        val category = call.receive<Category>()
        val savedCategory = category.copy(icon = category.icon)
        val id = localSource.saveCategory(savedCategory)

        call.respond(savedCategory.copy(id))
    }
}

private suspend fun LocalSource.getNextApplicationId() =
    getApplications().map { it.id }.max() ?: 0 + 1

const val NAME_QUERY_KEY = "name"
const val CATEGORY_QUERY_KEY = "category"
