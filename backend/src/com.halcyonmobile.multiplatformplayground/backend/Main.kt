package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.*
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.storage.LocalSourceImpl
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.util.error

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
        register(ContentType.Application.Json, KotlinxConverter())
    }

    // todo use DI
    val localSource: LocalSource = LocalSourceImpl(this)
    install(Routing) {
        api(localSource)
    }
}