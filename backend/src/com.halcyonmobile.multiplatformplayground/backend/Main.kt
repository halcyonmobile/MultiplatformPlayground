package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.*
import com.halcyonmobile.multiplatformplayground.di.installKodeinFeature
import com.halcyonmobile.multiplatformplayground.storage.LocalSource
import com.halcyonmobile.multiplatformplayground.storage.file.FileStorage
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.serialization.serialization
import io.ktor.util.error
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
        serialization()
    }
    install(CallLogging) {
        level = Level.INFO
    }
    installKodeinFeature()


    val localSource by kodein().instance<LocalSource>()
    val fileStorage by kodein().instance<FileStorage>()
    install(Routing) {
        // todo update uploadDir
        api(localSource, fileStorage)
    }
}