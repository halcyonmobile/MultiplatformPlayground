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
    apiCreateApplication(localSource)
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
private fun Routing.apiCreateApplication(localSource: LocalSource) {
    post("/applications") {
        val parts = call.receiveMultipart().readAllParts()
        // todo handle icon
        val icon = (parts.firstOrNull { it.name == APP_ICON } as? PartData.FileItem)
        // todo handle screenshot ids
//        val screenshotIds = parts.firstOrNull { it.name == "screenshot_ids[]" } as? PartData.FormItem)
        val applicationRequest =
            parts.filterIsInstance<PartData.FormItem>().createAppRequestFromPartMap()

        try {
            val screenshots = localSource.getScreenshots(applicationRequest.screenshots)
            localSource.createApplication()
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
        val mediaUrl =
            fileStorage.uploadScreenshot(screenshot.image.decodeBase64Bytes(), screenshot.name)

        val savedScreenshot = Screenshot(name = screenshot.name, image = mediaUrl)
        val id = localSource.saveScreenshot(savedScreenshot)
        call.respond(savedScreenshot.copy(id = id))
    }
}


private fun List<PartData.FormItem>.createAppRequestFromPartMap(): ApplicationRequest {
    val name = first { it.name == APP_NAME }.value
    val developer = first { it.name == APP_DEVELOPER }.value
    val description = first { it.name == APP_DESCRIPTION }.value
    val categoryId = first { it.name == APP_CATEGORY_ID }.value.toLong()
    val rating = first { it.name == APP_RATING }.value.toFloat()
    val ratingCount = first { it.name == APP_RATING_COUNT }.value.toInt()
    val downloads = first { it.name == APP_DOWNLOADS }.value
    val version = first { it.name == APP_VERSION }.value
    val storeUrl = first { it.name == APP_STORE_URL }.value
    val size = first { it.name == APP_SIZE }.value
    val screenshots = first { it.name == APP_SCREENSHOTS }.value

    // todo solve categoryId
    return ApplicationRequest(
        id = 0,
        name = name,
        developer = developer,
        categoryId = categoryId,
        description = description,
        rating = rating,
        ratingCount = ratingCount,
        downloads = downloads,
        version = version,
        size = size,
        storeUrl = storeUrl,
        screenshots = screenshots
    )
}

const val NAME_QUERY_KEY = "name"
const val CATEGORY_QUERY_KEY = "category"
