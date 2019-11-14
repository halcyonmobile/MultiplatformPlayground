package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.model.Screenshot
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import java.lang.Exception

// todo general error handling
internal fun Routing.api(localSource: LocalSource, uploadDir: String) {
    apiApplications(localSource)
    apiCreateApplication(localSource)
    apiUpdateApplication(localSource)
    apiGetApplication(localSource)
    apiFilterApplications(localSource)
    apiGetCategories(localSource)
    apiPostScreenshot(localSource, uploadDir)
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
// todo handle file
private fun Routing.apiCreateApplication(localSource: LocalSource) {
    post("/applications") {
        call.receive<Application>().let {
            try {
                localSource.createApplication(it)
                call.respond(HttpStatusCode.Created)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict)
            }
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
 * POST /api/v1/screenshots
 */
private fun Routing.apiPostScreenshot(localSource: LocalSource, uploadDir: String) {
    post("/screenshots") {
        call.receiveMultipart().forEachPart { part ->
            var name = "img-${System.currentTimeMillis()}"
            when (part) {
                is PartData.FormItem -> {
                    if (part.name == "name") {
                        name = part.value + name
                    }
                }
                is PartData.FileItem -> {
                    // todo handle file
                }
            }
            localSource.createScreenshot(Screenshot(name = name, image = ""))
            part.dispose()
        }
    }
}

const val NAME_QUERY_KEY = "name"
const val CATEGORY_QUERY_KEY = "category"
