package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Rep (
    @BsonId
    val id: String= ObjectId().toString(),
    val name: String,
    val phone: String,
    val autheId: String,
    val mySellers: List<String>,
    val myBuyers: List<String>,
    val isActive: Boolean
)