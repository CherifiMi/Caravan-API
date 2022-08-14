package com.example.routes

import com.example.models.Id
import com.example.models.Seller
import com.example.models.UserIdToType
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
        post("/auth") {
            try {
                call.parameters
                val requestBody = call.receive<Id>()
                call.respond(listOf(collection.findOne(Seller::autheId eq requestBody.id)))
            } catch (e: Exception) {
                call.respondText("ERROR: " + e.toString())
            }

        }
        //create new seller
        post {
            call.parameters
            val requestBody = call.receive<Seller>()

            val accountId =
                Account.create(
                    AccountCreateParams
                        .builder()
                        .setType(AccountCreateParams.Type.STANDARD)
                        .build()
                ).id

            fun makeUrl(accountId: String): String? {
                val params2 = AccountLinkCreateParams
                    .builder()
                    .setAccount(accountId)
                    .setRefreshUrl("https://example.com/return")
                    .setReturnUrl("https://example.com/return")
                    .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                    .build()

                return AccountLink.create(params2).url
            }

            val params2 = AccountLinkCreateParams
                .builder()
                .setAccount(accountId)
                .setRefreshUrl(makeUrl(accountId))
                .setReturnUrl("https://example.com/return")
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build()

            val accountLink =  AccountLink.create(params2).url

            val mySeller = Seller(
                id = null,
                owner = requestBody.owner,
                brand = requestBody.brand,
                type = requestBody.type,
                autheId = requestBody.autheId,
                phone = requestBody.phone,
                stripeId = accountId,
                isActive = requestBody.isActive
            )


            val suc = collection.insertOne(mySeller).wasAcknowledged()
                    &&collection1.insertOne(UserIdToType(type = "seller", autheId = mySeller.autheId))
                .wasAcknowledged()


            if (suc){
                call.respond(accountLink)
            }
            else{
                call.respond(suc)
            }

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
