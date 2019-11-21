package com.halcyonmobile.multiplatformplayground.backend

import com.halcyonmobile.multiplatformplayground.di.getKodeinModule
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080) {
        getKodeinModule(this)
    }.apply {
        start()
    }
}