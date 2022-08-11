package com.example.routes

import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.payment() {
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
            val clientSecret: String = intent.clientSecret
            call.respond(clientSecret)
        }
    }
}
