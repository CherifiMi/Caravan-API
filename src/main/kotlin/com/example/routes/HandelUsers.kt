package com.example.routes

import com.example.models.*
import com.stripe.model.Account
import com.stripe.model.AccountLink
import com.stripe.param.AccountCreateParams
import com.stripe.param.AccountLinkCreateParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.replaceOne
import org.litote.kmongo.eq




fun Route.user_type(
    collection: CoroutineCollection<UserIdToType>
) {
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
}