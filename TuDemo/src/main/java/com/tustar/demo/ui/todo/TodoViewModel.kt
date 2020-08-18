package com.tustar.demo.ui.todo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tustar.demo.data.Todo
import java.util.*
import kotlin.collections.ArrayList


class TodoViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodoViewModel(context) as T
    }
}

class TodoViewModel(context: Context) : ViewModel() {

    val todos get() = _todos
    private val _todos = MutableLiveData<List<Todo>>().apply {
        val todo = Todo(
            "MotionLayout",
            "MotionLayout实战"
        )
        val todos = ArrayList<Todo>(Collections.nCopies(8, todo))
        postValue(todos)
    }
}