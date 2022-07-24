package com.example.plugins

import com.example.buyers
import com.example.reps
import com.example.sellers
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineDatabase


fun Application.configureRouting(database: CoroutineDatabase) {

    routing {
        get("/") {
            call.respondText("Hello Mito!")
        }
    }
    //______________caravan api v1
    routing { route("/v1"){

        buyers(database.getCollection("buyers"))
        sellers(database.getCollection("sellers"))
        reps(database.getCollection("reps"))
    }}
}



