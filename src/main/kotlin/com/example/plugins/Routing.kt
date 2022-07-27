package com.example.plugins

import com.example.buyers
import com.example.reps
import com.example.sellers
import com.example.user_type
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineDatabase


fun Application.configureRouting(database: CoroutineDatabase) {

    // heroku create ktor-sample-heroku

    routing {
        get("/") {
            call.respondText("Hello Mito!")
        }
    }
    //______________caravan api v1
    routing { route("/v1"){

        buyers(database.getCollection("buyers"), database.getCollection("user_type"))
        sellers(database.getCollection("sellers"), database.getCollection("user_type"))
        reps(database.getCollection("reps"), database.getCollection("user_type"))

        user_type(database.getCollection("user_type"))
    }}
}



