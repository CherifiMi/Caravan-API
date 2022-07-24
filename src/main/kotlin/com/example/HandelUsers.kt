package com.example

import com.example.models.Buyer
import com.example.models.Id
import com.example.models.Rep
import com.example.models.Seller
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq


fun Route.buyers(collection: CoroutineCollection<Buyer>) {
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
            val isSuccess = collection.insertOne(requestBody).wasAcknowledged()
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

fun Route.sellers(collection: CoroutineCollection<Seller>) {
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
        //create new buyer
        post {
            call.parameters
            val requestBody = call.receive<Seller>()
            val isSuccess = collection.insertOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
        }

        // change buyer
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


fun Route.reps(collection: CoroutineCollection<Rep>) {
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
        //create new buyer
        post {
            call.parameters
            val requestBody = call.receive<Rep>()
            val isSuccess = collection.insertOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
        }

        // change buyer
        put {
            call.parameters
            val requestBody = call.receive<Rep>()
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
