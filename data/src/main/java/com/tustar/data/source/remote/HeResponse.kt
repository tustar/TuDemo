package com.tustar.data.source.remote


open class HeResponse(
    val code: String,
    val fxLink: String,
    val refer: Refer,
    val updateTime: String
)

data class Refer(
    val license: List<String>,
    val sources: List<String>
)