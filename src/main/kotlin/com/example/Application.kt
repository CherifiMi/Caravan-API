package com.example

import com.example.plugins.configureHTTP
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureSockets
import com.stripe.Stripe
import com.stripe.model.Account
import com.stripe.param.AccountCreateParams
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val client = KMongo
    .createClient(
        "mongodb+srv://mito:cherifi2003@mitocluster.bpzkl.mongodb.net/caravan_db?retryWrites=true&w=majority"
    ).coroutine
val database = client.getDatabase("caravan_db")
fun main() {

    Stripe.apiKey = "sk_test_51LWC3uSId0KyzvnEJlzBPdTOOEv4S6kMWzfnVdN9TqGmjjLuUtABPz69cSTrSU3AU3RYC3PvhEaRiagOUJxz1ESj00FB5rlJmo"

    embeddedServer(Netty,  port = System.getenv("PORT").toInt()) {
        configureHTTP()
        configureSerialization()
        configureSockets()
        configureRouting(database)
    }.start(wait = true)
}
