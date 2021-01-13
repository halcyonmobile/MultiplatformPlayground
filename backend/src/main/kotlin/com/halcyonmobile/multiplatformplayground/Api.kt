package com.halcyonmobile.multiplatformplayground

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.ApplicationRequest
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.shared.util.SCREENSHOT_NAME_PART
import com.halcyonmobile.multiplatformplayground.util.requirePage
import com.halcyonmobile.multiplatformplayground.util.requirePerPage
import com.halcyonmobile.multiplatformplayground.util.toFile
import io.ktor.application.call
import io.ktor.features.BadRequestException
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.util.InternalAPI
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.getOrFail
import java.io.File

internal fun Routing.api(localSource: LocalSource) {
    getApplications(localSource)
    createApplication(localSource)
    deleteApplication(localSource)
    postIcon(localSource)
    postScreenshot(localSource)
    updateApplication(localSource)
    getApplication(localSource)
    filterApplications(localSource)
    getCategories(localSource)
    postCategory(localSource)
    putCategory(localSource)
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
        val appId = localSource.saveApplication(call.receive<ApplicationRequest>())
        call.respond(appId)
    }
}

/**
 * DELETE /applications
 */
@OptIn(InternalAPI::class)
private fun Routing.deleteApplication(localSource: LocalSource) {
    delete("/applications") {
        localSource.deleteApplication(call.parameters["id"]!!.toLong())
        call.respond(HttpStatusCode.OK)
    }
}

/**
 * POST /icon
 */
private fun Routing.postIcon(localSource: LocalSource) {
    post("/applications/{appId}/icon") {
        val appId = call.parameters["appId"]!!.toLong()
        var icon: File? = null

        call.receiveMultipart().forEachPart {
            if (it is PartData.FileItem) {
                icon = it.toFile()
            }
            it.dispose()
        }
        localSource.saveIcon(
            icon ?: throw BadRequestException("icon part is missing"),
            appId
        )
        icon?.delete()
        call.respond(HttpStatusCode.OK)
    }
}

/**
 * POST /screenshot
 */
private fun Routing.postScreenshot(localSource: LocalSource) {
    post("/applications/{appId}/screenshot") {
        val appId = call.parameters["appId"]!!.toLong()

        var name: String? = null
        var image: File? = null

        call.receiveMultipart().forEachPart {
            when (it) {
                is PartData.FormItem -> if (it.name == SCREENSHOT_NAME_PART) {
                    name = it.value
                }
                is PartData.FileItem -> image = it.toFile()
                else -> Unit
            }
            it.dispose()
        }
        localSource.saveScreenshot(
            appId,
            name ?: throw BadRequestException("name part is missing"),
            image ?: throw BadRequestException("image part is missing")
        )
        image?.delete()
        call.respond(HttpStatusCode.OK)
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
 * POST /categories
 */
@OptIn(InternalAPI::class)
private fun Routing.postCategory(localSource: LocalSource) {
    post("/categories") {
        val category = call.receive<Category>()
        val id = localSource.saveCategory(category)

        call.respond(category.copy(id))
    }
}

/**
 * PUT /categories
 */
@OptIn(InternalAPI::class)
private fun Routing.putCategory(localSource: LocalSource) {
    put("/categories") {
        localSource.updateCategory(call.receive<Category>())
        call.respond(HttpStatusCode.OK)
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

const val NAME_QUERY_KEY = "name"
const val CATEGORY_QUERY_KEY = "categoryId"
