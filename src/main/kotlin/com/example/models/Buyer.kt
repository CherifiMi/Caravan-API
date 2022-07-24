package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Buyer(
    @BsonId
    val id: String= ObjectId().toString(),
    val owner: String,
    val brand: String,
    val phone: String,
    val autheId: String,
    val address: String,
    val isActive: Boolean
)
