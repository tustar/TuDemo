package com.tustar.demo.data

data class Todo(
    val title: String,
    val description: String,
    var isDone: Boolean = false
)