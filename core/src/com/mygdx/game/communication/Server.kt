package com.mygdx.game.communication

import com.mygdx.game.GameObj
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
        embeddedServer(Netty, 8080, configure = {
            // Size of the event group for accepting connections
            connectionGroupSize = 4
            // Size of the event group for processing connections,
            // parsing messages and doing engine's internal work
            workerGroupSize = 4
            // Size of the event group for running application code
            callGroupSize = 4
        }) {
            install(ContentNegotiation) {
                jackson {
                }
            }
            routing {
                get("/") {
                    val now = Calendar.getInstance().timeInMillis
                    while (now > Synchronizer.timestamp) {
                    }
                    call.respondText("", ContentType.Text.Html)
                }

                post("/cmd") {
                    val now = Calendar.getInstance().timeInMillis
                    val post = call.receive<Command>()
                    Synchronizer.cmds = Synchronizer.cmds.plus(post)

//                    println("Acquiring semaphore: " + post.botNo)
//                    println("Semaphore " + post.botNo + "available permits: " + GameObj.semaphores[post.botNo].isAcquired)


//                    println("PRZESZEDL: " + post.botNo)
                    GameObj.semaphores[post.botNo].acquire(true)


//                    println("Got inside: " + post.botNo)
                    println(Synchronizer.gameStatus)
                    call.respond(Synchronizer.gameStatus)
//                    Synchronizer.numOfBotsResponses += 1
//                    while (!Synchronizer.ready.value) {}
//                    Synchronizer.ready.value = false
                }
            }
        }.start()
    }
}