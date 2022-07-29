package com.example.routes

import com.example.models.Buyer
import com.example.models.Id
import com.example.models.Product
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.MongoOperator
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

fun Route.products(collection: CoroutineCollection<Product>) {
    route("/product") {

        //get all products
        get {
            try {
                call.respond(collection.find().toList())
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }
        }

        //create new product
        post {
            call.parameters
            val requestBody = call.receive<Product>()
            val isSuccess = collection.insertOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
        }

        // change product
        put {
            call.parameters
            val requestBody = call.receive<Product>()
            val isSuccess = collection.replaceOne(requestBody).wasAcknowledged()
            call.respond(isSuccess)
        }

        // delete product by id
        delete {
            call.parameters
            val requestBody = call.receive<Id>()
            val isSuccess = collection.deleteOneById(requestBody.id).wasAcknowledged()
            call.respond(isSuccess)
        }

        // get all product by seller key
        post("/all"){
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.find(Product::sellerKey eq requestBody.id).toList())
            } catch (e: Exception) {
                call.respondText("ERROR: $e")
            }

        }

    }
}

/*

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

     //get by auth key
    route("/type") {
        post {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(
                    listOf(collection.findOne(UserIdToType::autheId eq requestBody.id))
                )
            } catch (e: Exception) {
                call.respondText("ERROR: $e")
            }

        }
    }
*/