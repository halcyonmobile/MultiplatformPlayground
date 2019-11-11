package com.halcyonmobile.multiplatformplayground.api

import com.halcyonmobile.multiplatformplayground.storage.Database
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put

internal fun Routing.api(database: Database) {

}

/**
 * GET /api/v1/applications
 */
private fun Routing.apiApplications() {
    get("/applications") {
        call.respond(HttpStatusCode.OK)
    }
}

/**
 *  POST /api/v1/applications
 */
private fun Routing.apiCreateApplication() {
    post("/applications") {
        //       call.receive(Application)
//        todo save application
        val created = true
        if (created) {
            call.respond(HttpStatusCode.Created)
        } else {
            call.respond(HttpStatusCode.Conflict)
        }
    }
}

/**
 * PUT /api/v1/applications
 */
private fun Routing.apiUpdateApplication() {
    put("/applications") {
        // call.receive(Application)
        // update
        val updated = true
        if (updated) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.Conflict)
        }
    }
}

/**
 * GET /api/v1/applications/:id
 */
private fun Routing.apiGetApplication() {
    get("/applications/{id}") {
        val id = call.parameters["id"]
        // return application
    }
}

/**
 * GET /api/v1/applications/filter
 */
// TODO add this

/**
 * GET /api/v1/categories
 */
private fun Routing.apiGetCategories() {
    get("/categories") {
        // todo return categories
    }
}

/**
 * POST /api/v1/screenshots
 */
private fun Routing.apiPostScreenshot() {
    post("/screenshots") {
        // todo handle screenshot
    }
}