package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class product(
    @BsonId
    val id: String= ObjectId().toString(),
    val imageId: String,
    val name: String,
    val content: String,
    val initPrice: Int,
    val newPrice: Int,
)
