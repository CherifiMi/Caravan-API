package com.example.routes

import com.example.models.Id
import com.example.models.Order
import com.example.models.Product
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq

fun Route.orders(collection: CoroutineCollection<Order>, collectionP: CoroutineCollection<Product>) {
    route("/orders") {
        get {
            try {
                call.parameters
                call.respond(collection.find().toList())
            } catch (e: Exception) {
                call.respondText("ERROR: $e")
            }
        }

        //oldTest.copy(age = oldTest.age - 2, name = "yooo")
        //collectionP.replaceOne(Product::id eq requestBody.productId, newProduct).wasAcknowledged()
        //val thisProduct: Product = collectionP.findOne(Product::id eq requestBody.productId)!!
        //val newProduct = thisProduct?.copy(amountInInv = thisProduct.amountInInv.minus(requestBody.amount))

        //create new order
        post("/make") {
            call.parameters
            val requestBody = call.receive<Order>()

            // get the current product
            val currentProduct =  collectionP.findOne(Product::id eq requestBody.productId)

            // make a new product from the old one
            val changedProduct = currentProduct?.copy(amountInInv = currentProduct.amountInInv - requestBody.amount)

            //replace the old one with the new one


           //val isSuccess =
           //    collection.insertOne(requestBody).wasAcknowledged()
           //            //&& collectionP.replaceOne(changedProduct).wasAcknowledged()

            call.respond(listOf(currentProduct, changedProduct))
        }


        // delete order by id
        post("/delete") {
            call.parameters
            val requestBody = call.receive<Id>()
            val isSuccess = collection.deleteOneById(requestBody.id).wasAcknowledged()
            call.respond(isSuccess)
        }

        // get all product by seller key
        post("/my") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(collection.find(Order::seller eq requestBody.id).toList())
            } catch (e: Exception) {
                call.respondText("ERROR: $e")
            }

        }
    }
}