package com.tustar.data.source.remote


open class Response(
    val code: String,
    val fxLink: String? = null,
    val refer: Refer,
    val updateTime: String? = null,
)

data class Refer(
    val license: List<String>,
    val sources: List<String>
)