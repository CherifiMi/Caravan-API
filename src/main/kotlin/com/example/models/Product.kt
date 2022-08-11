package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Product(
    @BsonId
    val id: String= ObjectId().toString(),
    val sellerKey: String,
    val sellerStripe: String,
    val imageUrls: List<String>,
    val name: String,
    val minOrder: String,
    val cat: List<String>,
    val content: String,
    val initPrice: Int,
    val newPrice: Int,
    val amountInInv: Int
)