package com.tustar.demo.data.model

data class Todo(
    val title: String,
    val description: String,
    var isDone: Boolean = false
)