package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.NotFound
import com.halcyonmobile.multiplatformplayground.Unauthorized
import com.halcyonmobile.multiplatformplayground.di.installKodeinFeature
import com.halcyonmobile.multiplatformplayground.model.ApplicationRequest
import com.halcyonmobile.multiplatformplayground.model.Category
import com.halcyonmobile.multiplatformplayground.model.toApplication
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.serialization.json
import io.ktor.util.error
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein
import org.slf4j.event.Level

internal fun Application.main() {

    install(StatusPages) {
        exception<Unauthorized> {
            call.respond(HttpStatusCode.Unauthorized)
        }
        exception<NotFound> {
            call.respond(HttpStatusCode.NotFound)
        }
        exception<Throwable> { cause ->
            environment.log.error(cause)
            call.respond(HttpStatusCode.InternalServerError)
        }
    }

    install(ContentNegotiation) {
        json()
    }
    install(CallLogging) {
        level = Level.INFO
    }
    installKodeinFeature()

    val localSource by kodein().instance<LocalSource>()
    install(Routing) {
        // todo update uploadDir
        api(localSource)
    }

    mock(localSource)
}

// TODO remove mocked data
private fun Application.mock(localSource: LocalSource) = with(environment.classLoader) {
    // Init database with pre-defined categories located in resources/categories.json
    getResourceAsStream("categories.json")?.bufferedReader()?.readText()
        ?.let {
            Json.decodeFromString<List<Category>>(it).forEach { category ->
                launch {
                    localSource.saveCategory(category)
                }
            }
        }

    getResourceAsStream("applications.json")?.bufferedReader()?.readText()?.let {
        Json.decodeFromString<List<ApplicationRequest>>(it).forEach { applicationRequest ->
            launch {
                val category = localSource.getCategory(applicationRequest.categoryId)
                localSource.saveApplication(
                    applicationRequest.toApplication(
                        applicationRequest.encodedIcon,
                        category,
                        emptyList()
                    )
                )
            }
        }
    }
}