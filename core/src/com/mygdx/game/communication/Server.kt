package com.mygdx.game.communication

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.util.*

class Server {
    fun start() {
        embeddedServer(Netty, 8080) {
            install(ContentNegotiation) {
                jackson {
                }
            }
            routing {
                get("/") {
                    val now = Calendar.getInstance().time
                    while (now > Synchronizer.timestamp) {}
                    call.respondText("", ContentType.Text.Html)
                }

                post("/cmd") {
                    val now = Calendar.getInstance().time
                    val post = call.receive<Command>()
                    Synchronizer.cmds.add(post)
                    while (now > Synchronizer.timestamp) {}
                    call.respond(Synchronizer.gameStatus)
                }
            }
        }.start()
    }
}