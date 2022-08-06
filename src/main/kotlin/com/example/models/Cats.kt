package com.example.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class Cats(
    @BsonId
    val id: String= ObjectId().toString(),
    val name: String,
    val subCats: List<String>,
    val picUrl: String
)