package com.tustar.demo.ui.todo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tustar.demo.data.Todo


class TodoViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(context) as T
    }
}

class TodoViewModel(context: Context) : ViewModel() {

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