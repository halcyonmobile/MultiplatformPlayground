package com.halcyonmobile.multiplatformplayground

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.util.*
import io.ktor.application.call
import io.ktor.features.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.util.*
import java.io.File

internal fun Routing.api(localSource: LocalSource) {
    getApplications(localSource)
    createApplication(localSource)
    postIcon(localSource)
    postScreenshot(localSource)
    updateApplication(localSource)
    getApplication(localSource)
    filterApplications(localSource)
    getCategories(localSource)
    postCategory(localSource)
    getFavourites(localSource)
}

/**
 * GET /applications?categoryId={categoryId}&page={page}&perPage={perPage}
 */
@OptIn(KtorExperimentalAPI::class)
private fun Routing.getApplications(localSource: LocalSource) {
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
@OptIn(InternalAPI::class)
private fun Routing.createApplication(localSource: LocalSource) {
    post("/applications") {
        val applicationRequest = call.receive<ApplicationRequest>()

        val appId = localSource.getNextApplicationId().toLong()
        localSource.saveApplication(applicationRequest)
    }
}

/**
 * POST /icon
 */
private fun Routing.postIcon(localSource: LocalSource) {
    post("/applications/{appId}/icon") {
        val appId = call.parameters["appId"].toString().toLong()
        var icon: File? = null

        call.receiveMultipart().forEachPart {
            if (it is PartData.FileItem) {
                icon = it.toFile(it.iconName)
            }
            it.dispose()
        }
        localSource.saveIcon(
            icon ?: throw BadRequestException("icon part is missing"),
            appId
        )
    }
}

/**
 * POST /screenshot
 */
private fun Routing.postScreenshot(localSource: LocalSource) {
    post("/applications/{appId}/screenshot") {
        val appId = call.parameters["appId"].toString().toLong()

        var name: String? = null
        var image: File? = null

        call.receiveMultipart().forEachPart {
            when (it) {
                is PartData.FormItem -> if (it.name == "name") {
                    name = it.value
                }
                is PartData.FileItem -> image = it.toFile(it.imageName)
                else -> Unit
            }
            it.dispose()
        }
        localSource.saveScreenshot(
            appId,
            name ?: throw BadRequestException("name part is missing"),
            image ?: throw BadRequestException("image part is missing")
        )
    }
}

/**
 * PUT /applications
 */
private fun Routing.updateApplication(localSource: LocalSource) {
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
private fun Routing.getApplication(localSource: LocalSource) {
    get("/applications/{id}") {
        val id = call.parameters["id"].toString().toLong()
        call.respond(localSource.getApplicationWithDetail(id))
    }
}

/**
 * GET /api/v1/applications/filter
 */
private fun Routing.filterApplications(localSource: LocalSource) {
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
private fun Routing.getCategories(localSource: LocalSource) {
    get("/categories") {
        localSource.getCategories().let {
            call.respond(it)
        }
    }
}

/**
 * POST /api/v1/categories
 */
@OptIn(InternalAPI::class)
private fun Routing.postCategory(localSource: LocalSource) {
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
private fun Routing.getFavourites(localSource: LocalSource) {
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
