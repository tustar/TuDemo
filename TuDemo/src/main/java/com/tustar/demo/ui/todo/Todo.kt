package com.tustar.demo.ui.todo

import androidx.annotation.IntDef
import com.tustar.demo.R

const val STATE_UNDO = 0
const val STATE_DOING = 1
const val STATE_DONE = 2
const val STATE_CANCELED = 3

@IntDef(value = [STATE_UNDO, STATE_DOING, STATE_DONE, STATE_CANCELED])
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class TodoState

fun stateToStringResId(@TodoState state: Int) =
    when (state) {
        STATE_UNDO -> R.string.todo_undo
        STATE_DOING -> R.string.todo_doing
        STATE_DONE -> R.string.todo_done
        STATE_CANCELED -> R.string.todo_canceled
        else -> -1
    }

data class Todo(
    val id: Long,
    val title: String,
    val description: String,
    val category:String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    @TodoState var state: Int = STATE_UNDO
)

