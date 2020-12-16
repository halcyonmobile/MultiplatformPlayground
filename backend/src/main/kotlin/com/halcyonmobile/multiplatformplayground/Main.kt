package com.halcyonmobile.multiplatformplayground

import com.halcyonmobile.multiplatformplayground.di.getKoinModule
import com.halcyonmobile.multiplatformplayground.model.ApplicationRequest
import com.halcyonmobile.multiplatformplayground.model.Category
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
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
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
    install(Koin) {
        modules(getKoinModule())
    }
    val localSource by inject<LocalSource>()
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
                localSource.saveApplication(applicationRequest)
            }
        }
    }
}