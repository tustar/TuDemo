package com.tustar.demo.ui.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {

    val todos get() = _todos
    private val _todos = MutableLiveData<List<Todo>>().apply {
        val todos = listOf<Todo>(
            Todo(
                "MotionLayout",
                "MotionLayout实战",
                true
            ),
            Todo(
                "Hilt",
                "Hilt实战",
                false
            )

        )
        postValue(todos)
    }
}