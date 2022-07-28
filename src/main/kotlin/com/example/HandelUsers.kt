package com.example

import com.example.models.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.insertOne
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq


fun Route.buyers(
    collection: CoroutineCollection<Buyer>,
    collection1: CoroutineCollection<UserIdToType>
) {
    route("/buyers") {
        //get all
        get {
            try {
                call.respond(collection.find().toList())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/auth") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne(Buyer::autheId eq requestBody.id).toString())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/id") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne(Buyer::id eq requestBody.id).toString())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //create new buyer
        post {
            call.parameters
            val requestBody = call.receive<Buyer>()
            val isSuccess =
                collection.insertOne(requestBody).wasAcknowledged()
                && collection1.insertOne(UserIdToType(type = "buyer", autheId = requestBody.autheId))
                .wasAcknowledged()
            call.respond(isSuccess)
        }

        // change buyer
        put {
            call.parameters
            val requestBody = call.receive<Buyer>()
            val isSuccess = collection.replaceOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
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

fun Route.sellers(
    collection: CoroutineCollection<Seller>,
    collection1: CoroutineCollection<UserIdToType>
) {
    route("/sellers") {
        //get all
        get {
            try {
                call.respond(collection.find().toList())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/auth") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne(Seller::autheId eq requestBody.id).toString())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/id") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne(Seller::id eq requestBody.id).toString())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //create new seller
        post {
            call.parameters
            val requestBody = call.receive<Seller>()
            val isSuccess =
                collection.insertOne(requestBody).wasAcknowledged()
                && collection1.insertOne(UserIdToType(type = "seller", autheId = requestBody.autheId))
                    .wasAcknowledged()
            call.respond(isSuccess)
        }

        // change seller
        put {
            call.parameters
            val requestBody = call.receive<Seller>()
            val isSuccess = collection.replaceOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
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

fun Route.reps(
    collection: CoroutineCollection<Rep>,
    collection1: CoroutineCollection<UserIdToType>
) {
    route("/reps") {
        //get all
        get {
            try {
                call.respond(collection.find().toList())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/auth") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne(Rep::autheId eq requestBody.id).toString())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //get by auth key
        get("/id") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.findOne(Rep::id eq requestBody.id).toString())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //create new rep
        post {
            call.parameters
            val requestBody = call.receive<Rep>()
            val isSuccess = collection.insertOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
        }

        // change rep
        put {
            call.parameters
            val requestBody = call.receive<Rep>()
            val isSuccess =
                collection.replaceOne(requestBody).wasAcknowledged()
                && collection1.insertOne(UserIdToType(type = "rep", autheId = requestBody.autheId))
                    .wasAcknowledged()
            call.respond(isSuccess)
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

fun Route.user_type(
    collection: CoroutineCollection<UserIdToType>
){
    //get by auth key
    route("/type") {
        post{

            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respondText(collection.findOne(UserIdToType::autheId eq requestBody.id)?.type ?: "nodata")
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
    }
}