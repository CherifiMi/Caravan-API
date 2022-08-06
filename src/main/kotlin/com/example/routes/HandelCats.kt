package com.example.routes

import com.example.models.Cats
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineCollection

fun Route.cats(collection: CoroutineCollection<Cats>) {

    route("/cats") {
        //get all cats
        get {
            try {
                call.respond(collection.find().toList())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }
        }
    }

}