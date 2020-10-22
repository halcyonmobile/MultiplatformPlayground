package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.util.requirePage
import com.halcyonmobile.multiplatformplayground.util.requirePerPage
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.util.*

internal fun Routing.api(localSource: LocalSource) {
    apiGetApplications(localSource)
    apiCreateApplication(localSource)
    apiUpdateApplication(localSource)
    apiGetApplication(localSource)
    apiFilterApplications(localSource)
    apiGetCategories(localSource)
    apiPostCategory(localSource)
    apiGetFavourites(localSource)
}

/**
 * GET /applications?categoryId={categoryId}&page={page}&perPage={perPage}
 */
@OptIn(KtorExperimentalAPI::class)
private fun Routing.apiGetApplications(localSource: LocalSource) {
    get("/applications") {
        val categoryId = call.request.queryParameters.getOrFail<Long>(CATEGORY_QUERY_KEY)
        localSource.getApplications(requirePage(), requirePerPage(), categoryId).let {
            call.respond(it)
        }
    }
}

/**
 *  POST /applications
 */
@UseExperimental(InternalAPI::class)
private fun Routing.apiCreateApplication(localSource: LocalSource) {
    post("/applications") {
        val applicationRequest = call.receive<ApplicationRequest>()

        val appId = localSource.getNextApplicationId().toLong()
        try {
            val screenshots = applicationRequest.screenshots.map {
                val savedScreenshot = it.copy(name = it.name, image = it.image)
                val id = localSource.saveScreenshot(savedScreenshot, appId)
                savedScreenshot.copy(id = id)
            }
            localSource.saveApplication(applicationRequest)

            call.respond(HttpStatusCode.Created)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict)
        }
    }
}


/**
 * PUT /applications
 */
private fun Routing.apiUpdateApplication(localSource: LocalSource) {
    put("/applications") {
        call.receive<Application>().let {
            localSource.updateApplication(it)
            call.respond(HttpStatusCode.OK)
        }
    }
}

/**
 * GET /applications/:id
 */
private fun Routing.apiGetApplication(localSource: LocalSource) {
    get("/applications/{id}") {
        val id = call.parameters["id"].toString().toLong()
        call.respond(localSource.getApplicationWithDetail(id))
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

/**
 * GET /favourites
 */
private fun Routing.apiGetFavourites(localSource: LocalSource) {
    get("/favourites") {
        localSource.getFavourites().let {
            call.respond(it)
        }
    }
}

private suspend fun LocalSource.getNextApplicationId() =
    getApplications().map { it.id }.max() ?: 0 + 1

const val NAME_QUERY_KEY = "name"
const val CATEGORY_QUERY_KEY = "categoryId"
