package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.model.*
import com.halcyonmobile.multiplatformplayground.shared.util.*
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.storage.file.FileStorage
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.readAllParts
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.util.InternalAPI
import io.ktor.util.decodeBase64Bytes
import java.lang.Exception

// todo general error handling
internal fun Routing.api(localSource: LocalSource, fileStorage: FileStorage) {
    apiApplications(localSource)
    apiCreateApplication(localSource, fileStorage)
    apiUpdateApplication(localSource)
    apiGetApplication(localSource)
    apiFilterApplications(localSource)
    apiGetCategories(localSource)
    apiPostScreenshot(localSource, fileStorage)
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
@UseExperimental(InternalAPI::class)
private fun Routing.apiCreateApplication(localSource: LocalSource, fileStorage: FileStorage) {
    post("/applications") {
        val applicationRequest = call.receive<ApplicationRequest>()

        val iconUrl = fileStorage.uploadIcon(
            applicationRequest.encodedIcon.decodeBase64Bytes(),
            applicationRequest.name
        )

        try {
            val screenshots = localSource.getScreenshots(applicationRequest.screenshots)
            val category = localSource.getCategory(applicationRequest.categoryId)

            localSource.createApplication(
                applicationRequest.toApplication(
                    iconUrl,
                    category,
                    screenshots
                )
            )
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
 * POST /api/v1/screenshots
 */
@UseExperimental(InternalAPI::class)
private fun Routing.apiPostScreenshot(localSource: LocalSource, fileStorage: FileStorage) {
    post("/screenshots") {
        val screenshot = call.receive<Screenshot>()
        val screenshotUrl =
            fileStorage.uploadScreenshot(screenshot.image.decodeBase64Bytes(), screenshot.name)

        val savedScreenshot = Screenshot(name = screenshot.name, image = screenshotUrl)
        val id = localSource.saveScreenshot(savedScreenshot)
        call.respond(savedScreenshot.copy(id = id))
    }
}

private fun ApplicationRequest.toApplication(
    iconUrl: String,
    category: Category?,
    screenshots: List<Screenshot>
) =
    ApplicationWithDetail(
        application = Application(
            name = name,
            developer = developer,
            favourite = favourite,
            category = category
        ),
        applicationDetail = ApplicationDetail(
            iconUrl,
            rating,
            ratingCount,
            storeUrl,
            description,
            downloads,
            version,
            size,
            screenshots
        )
    )

const val NAME_QUERY_KEY = "name"
const val CATEGORY_QUERY_KEY = "category"
