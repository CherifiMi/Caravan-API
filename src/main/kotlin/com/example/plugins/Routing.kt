package com.example.plugins

import com.example.models.Buyer
import com.example.models.Id
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq


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
        //get all
        get {
            try{
                call.respond(collection.find().toList())
            }catch (e: Exception){
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/auth"){
            try{
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne( Buyer::autheId eq requestBody.id).toString())
            }catch (e: Exception){
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/id"){
            try{
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne( Buyer::id eq requestBody.id).toString())
            }catch (e: Exception){
                call.respondText("ERROR: " + e.toString())
            }

        }
        //create new buyer
        post {
            call.parameters
            val requestBody = call.receive<Buyer>()
            val isSuccess = collection.insertOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
        }

        // change buyer
        put {
            call.respondText("put buyers!")
        }

        // delete by id
        delete {
            call.parameters
            val requestBody = call.receive<Id>()
            val isSuccess = collection.deleteOneById(requestBody.id).wasAcknowledged()
            call.respond(isSuccess)
        }
    }
}
