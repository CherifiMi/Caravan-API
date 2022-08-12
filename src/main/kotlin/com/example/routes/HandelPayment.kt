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

            val amount = formParameters["amount"]?.toInt()
            val currency = formParameters["currency"].toString()
            val linked = formParameters["linked"].toString()

            val transferDataParams: MutableMap<String, Any> = HashMap()
            transferDataParams["destination"] = linked

            val am = amount ?: 1000

           val params =
               PaymentIntentCreateParams.builder()
                   .setAmount(am.toLong())
                   .putExtraParam("transfer_data", transferDataParams)
                   .setCurrency(currency)
                   .setAutomaticPaymentMethods(
                       PaymentIntentCreateParams.AutomaticPaymentMethods
                           .builder()
                           .setEnabled(true)
                           .build()
                   )
                   .build()

           //val  paymentMethodTypes: ArrayList<String> = arrayListOf("card")

           //val transferDataParams: MutableMap<String, Any> = HashMap()
           //transferDataParams["destination"] = linked

           //val params: MutableMap<String, Any> = HashMap()
           //params["amount"] = am
           //params["payment_method_types"] = paymentMethodTypes
           //params["currency"] = currency
           //params["application_fee_amount"] = am/2
           //params["transfer_data"] = transferDataParams


            val intent = PaymentIntent.create(params)
            val clientSecret = intent.clientSecret

            call.respond(clientSecret)
        }
    }
}
