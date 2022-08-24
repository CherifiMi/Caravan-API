package com.example.routes

import com.stripe.model.Account
import com.stripe.model.AccountLink
import com.stripe.model.PaymentIntent
import com.stripe.net.RequestOptions
import com.stripe.param.AccountLinkCreateParams
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
        post {
            val formParameters = call.receiveParameters()
            val accountId = formParameters["id"] ?: ""

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

            val canPayout = Account.retrieve(accountId).payoutsEnabled
            val link = AccountLink.create(params2).url

            call.respond(listOf(canPayout, link))
        }
        //post ("payout"){
        //    val formParameters = call.receiveParameters()
        //    val accountId = formParameters["id"] ?: ""
        //    val canPayout = Account.retrieve(accountId).payoutsEnabled
        //    call.respond(canPayout)
        //}
    }
}

