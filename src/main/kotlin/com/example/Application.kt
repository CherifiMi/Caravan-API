package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


val client = KMongo
    .createClient(
        "mongodb+srv://mito:cherifi2003@mitocluster.bpzkl.mongodb.net/caravan_db?retryWrites=true&w=majority"
    ).coroutine
val database = client.getDatabase("caravan_db")
fun main() {
    embeddedServer(Netty,  port = System.getenv("PORT").toInt()) {
        configureHTTP()
        //configureMonitoring()
        configureSerialization()
        configureSockets()
        configureRouting(database)
    }.start(wait = true)
}
