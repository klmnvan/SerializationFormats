package com.example.workforserialization.models

import kotlinx.serialization.Serializable

@Serializable
data class Book (
    var title: String? = "",
    var author: String? = "",
    var pageCount: Int? = 0,
    var genre: String? = ""
)
