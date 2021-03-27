package com.tustar.demo.ui.todo

import androidx.annotation.IntDef

const val STATE_UNDO = 0
const val STATE_DOING = 1
const val STATE_DONE = 2

@IntDef(value = [STATE_UNDO, STATE_DOING, STATE_DONE])
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class TodoState

data class Todo(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    @TodoState var state: Int = STATE_UNDO
)

