package com.example.routes

import com.stripe.model.Account
import com.stripe.model.AccountLink
import com.stripe.model.PaymentIntent
import com.stripe.net.RequestOptions
import com.stripe.param.AccountLinkCreateParams
import com.stripe.param.PaymentIntentCreateParams
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.payment() {
    route("/payment") {
        post {

            val formParameters = call.receiveParameters()

            val amount = formParameters["amount"]!!.toInt()
            val currency = formParameters["currency"].toString()
            val linked = formParameters["linked"].toString()


            //val params =
            //    PaymentIntentCreateParams.builder()
            //        .setAmount(am.toLong())
            //        .putExtraParam("transfer_data", transferDataParams)
            //        .setCurrency(currency)
            //        .setAutomaticPaymentMethods(
            //            PaymentIntentCreateParams.AutomaticPaymentMethods
            //                .builder()
            //                .setEnabled(true)
            //                .build()
            //        )
            //        .build()

            val paymentMethodTypes: ArrayList<String> = arrayListOf("card")

            val params: MutableMap<String, Any> = HashMap()
            params["amount"] = amount.toString()
            params["payment_method_types"] = paymentMethodTypes
            params["currency"] = currency
            //params["application_fee_amount"] = ((amount*2.5f)/100f).toInt()

            val requestOptions = RequestOptions.builder().setStripeAccount(linked).build()
            val paymentIntent = PaymentIntent.create(params, requestOptions)

            val clientSecret = paymentIntent.clientSecret

            call.respond(clientSecret)
        }
    }
}


fun Route.acclink() {
    route("/accountLink") {
        post ("/link"){
            val formParameters = call.receiveParameters()
            val accountId = formParameters["id"] ?: ""

            val params2 = AccountLinkCreateParams
                .builder()
                .setAccount(accountId)
                .setRefreshUrl("https://example.com/return")
                .setReturnUrl("https://example.com/return")
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build()

           val link =  AccountLink.create(params2).url

            call.respond(link)
        }
        post ("payout"){
            val formParameters = call.receiveParameters()
            val accountId = formParameters["id"] ?: ""
            val canPayout = Account.retrieve(accountId).chargesEnabled
            call.respond(canPayout)
        }
    }
}

fun Route.stripe() {

    route("/stripe") {
        get {
            call.respondText("Hello mito!!!!!!")
        }
        get("{id}") {

        }

        post {

            val formParameters = call.receiveParameters()

            val amount = formParameters["amount"]?.toLong()
            val currency = formParameters["currency"].toString()

            //val params: PaymentIntentCreateParams =
            //    PaymentIntentCreateParams.builder()
            //        .setAmount(amount)
            //        .setCurrency(currency)
            //        .build()

            val params =
                PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency(currency)
                    .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                            .builder()
                            .setEnabled(true)
                            .build()
                    )
                    .build()

            val intent: PaymentIntent = PaymentIntent.create(params)
            val clientSecret: String = intent.clientSecret
            call.respond(clientSecret)
        }

        delete("{id}") {

        }
    }
}

