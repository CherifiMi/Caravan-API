package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserIdToType(
    @BsonId
    val id: String = ObjectId().toString(),
    val type: String,
    val autheId: String
)
