package com.example.plugins

import com.example.models.Buyer
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.litote.kmongo.coroutine.CoroutineCollection
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

    }}
}

fun Route.buyers(collection: CoroutineCollection<Buyer>) {
    route("/buyers"){
        get {
            try{
                call.respond(collection.find().toList())
            }catch (e: Exception){
                call.respondText("ERROR: " + e.toString())
            }

        }
        post {
            call.respondText("post buyers!")
        }
        put {
            call.respondText("put buyers!")
        }
        delete {
            call.respondText("delete buyers!")
        }
    }
}
