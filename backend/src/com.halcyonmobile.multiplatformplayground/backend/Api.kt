package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.model.Application
import com.halcyonmobile.multiplatformplayground.storage.Database
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import java.lang.Exception

// todo general error handling
internal fun Routing.api(database: Database) {
    apiApplications(database)
    apiCreateApplication(database)
    apiUpdateApplication(database)
    apiGetApplication(database)
    apiFilterApplications(database)
    apiGetCategories(database)
    apiPostScreenshot(database)
}

/**
 * GET /api/v1/applications
 */
private fun Routing.apiApplications(database: Database) {
    get("/applications") {
        database.getApplications().let {
            call.respond(it)
        }
    }
}

/**
 *  POST /api/v1/applications
 */
private fun Routing.apiCreateApplication(database: Database) {
    post("/applications") {
        call.receive<Application>().let {
            try {
                database.createApplication(it)
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
private fun Routing.apiUpdateApplication(database: Database) {
    put("/applications") {
        call.receive<Application>().let {
            try {
                database.updateApplication(it)
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
private fun Routing.apiGetApplication(database: Database) {
    get("/applications/{id}") {
        val id = call.parameters["id"].toString().toLong()
        database.getApplication(id).let {
            call.respond(it)
        }
        // return application
    }
}

/**
 * GET /api/v1/applications/filter
 */
private fun Routing.apiFilterApplications(database: Database) {
    get("/applications/filter") {
        val name = call.request.queryParameters[NAME_QUERY_KEY].toString()
        val categoryId = call.request.queryParameters[CATEGORY_QUERY_KEY]!!.toLong()

        database.getApplications(name, categoryId).let {
            call.respond(it)
        }
    }
}

/**
 * GET /api/v1/categories
 */
private fun Routing.apiGetCategories(database: Database) {
    get("/categories") {
        database.getCategories().let {
            call.respond(it)
        }
    }
}

/**
 * POST /api/v1/screenshots
 */
private fun Routing.apiPostScreenshot(database: Database) {
    post("/screenshots") {
        // todo handle file
    }
}

const val NAME_QUERY_KEY = "name"
const val CATEGORY_QUERY_KEY = "category"
