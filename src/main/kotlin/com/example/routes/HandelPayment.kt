package com.example.routes

import com.stripe.model.Account
import com.stripe.model.AccountLink
import com.stripe.model.PaymentIntent
import com.stripe.param.AccountCreateParams
import com.stripe.param.AccountLinkCreateParams
import com.stripe.param.PaymentIntentCreateParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.stripe() {
    route("/payment") {
        post {

            val formParameters = call.receiveParameters()

            val amount = formParameters["amount"]?.toLong()
            val currency = formParameters["currency"].toString()

            val params: PaymentIntentCreateParams =
                PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency(currency)
                    .build()

            val intent: PaymentIntent = PaymentIntent.create(params)
            var clientSecret: String = intent.clientSecret
            call.respond(clientSecret)
        }

        delete("{id}") {

        }

    }
}

/*

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

            val accountLink = AccountLink.create(params2).url

            val res = accountLink

            call.respond(res)
        }

*/