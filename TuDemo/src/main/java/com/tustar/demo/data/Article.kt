package com.tustar.demo.data

data class Article(
    val title: String,
    val description: String,
    val url: String,
    var img: String? = null,
    val author: String,
    val createAt: Long
)