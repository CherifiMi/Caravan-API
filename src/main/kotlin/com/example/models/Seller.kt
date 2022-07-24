package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Seller(
    @BsonId
    val id: String= ObjectId().toString(),
    val owner: String,
    val brand: String,
    val type: String,
    val autheId: String,
    val phone: String,
    val productsId: List<String>,
    val ordersId: List<String>,
    val isActive: Boolean
)
