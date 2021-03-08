package com.tustar.demo.ui.todo

data class Todo(
    val title: String,
    val description: String,
    var isDone: Boolean = false
)