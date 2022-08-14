package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Order(
    @BsonId
    val id: String= ObjectId().toString(),
    val productId: String,
    val amount: Int,
    val buyer: String,
    val seller: String
)

