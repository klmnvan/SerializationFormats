package com.example.workforserialization.models

import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    var id: String? = "",
    var name: String? = "",
    var isFluffy: Boolean? = false,
    var mustacheLength: Int? = 0
)