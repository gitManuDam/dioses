package com.dioses

import com.dioses.plugins.configureRouting
import com.dioses.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8090, host = "127.0.0.1", module = Application::module)
        .start(wait = true)}

fun Application.module() {
    configureSerialization()
    configureRouting()

}
