package com.tustar.demo.ui.todo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tustar.demo.data.model.Todo


class TodoViewModel @ViewModelInject constructor() : ViewModel() {

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