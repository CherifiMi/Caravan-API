package com.example.plugins

import com.example.routes.*
import com.stripe.model.Account
import com.stripe.model.AccountLink
import com.stripe.param.AccountCreateParams
import com.stripe.param.AccountLinkCreateParams
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.litote.kmongo.coroutine.CoroutineDatabase


fun Application.configureRouting(database: CoroutineDatabase) {

    // git push heroku main


    routing {
        get("/") {
            call.respondText("Hello Mito!")
        }

        get("/pay") {
            val params = AccountCreateParams
                .builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .build()

            val account: Account = Account.create(params)

            val params2 = AccountLinkCreateParams
                .builder()
                .setAccount(account.id)
                .setRefreshUrl("https://example.com/reauth")
                .setReturnUrl("https://example.com/return")
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build()

            val accountLink = AccountLink.create(params2)

            call.respond(accountLink)
        }
    }
    //______________caravan api v1
    routing {
        route("/v1") {
            buyers(database.getCollection("buyers"), database.getCollection("user_type"))
            sellers(database.getCollection("sellers"), database.getCollection("user_type"))
            reps(database.getCollection("reps"), database.getCollection("user_type"))

            user_type(database.getCollection("user_type"))

            products(database.getCollection("products"))

            cats(database.getCollection("cats"))

            //account()
            //accountlink()
        }
    }
}



