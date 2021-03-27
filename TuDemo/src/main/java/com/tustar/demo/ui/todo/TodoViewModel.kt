package com.tustar.demo.ui.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {

    val todos get() = _todos
    private val _todos = MutableLiveData<List<Todo>>().apply {
        val todos = listOf(
            Todo(
                id = 1,
                title = "MotionLayout",
                description = "MotionLayout实战",
                state = STATE_DONE
            ),
            Todo(
                id = 2,
                title = "Hilt",
                description = "Hilt实战",
                state = STATE_DONE
            )
        )
        postValue(todos)
    }

    fun getTodos(): Map<Int, List<Todo>> {
        val todos = listOf(
            Todo(
                id = 1,
                title = "MotionLayout",
                description = "MotionLayout实战",
                state = STATE_DONE
            ),
            Todo(
                id = 2,
                title = "Hilt",
                description = "Hilt实战",
                state = STATE_DONE
            ),
            Todo(
                id = 3,
                title = "Compose 布局",
                description = "Jetpack Compose实战",
                state = STATE_DOING
            ),
            Todo(
                id = 4,
                title = "Compose 列表",
                description = "Jetpack Compose实战",
                state = STATE_DOING
            ),
            Todo(
                id = 5,
                title = "码农世界Compose重构",
                description = "Jetpack Compose实战",
                state = STATE_DOING
            ),
            Todo(
                id = 6,
                title = "Compose+Navigation",
                description = "Jetpack Compose实战",
                state = STATE_UNDO
            ),
            Todo(
                id = 7,
                title = "干货文章整理成Json数据",
                description = "文章收集",
                state = STATE_UNDO
            ),
            Todo(
                id = 8,
                title = "干货新增分享收集入口和出口",
                description = "文章收集",
                state = STATE_UNDO
            ),
            Todo(
                id = 9,
                title = "干货新增浏览器阅读",
                description = "文章收集",
                state = STATE_UNDO
            ),
            Todo(
                id = 10,
                title = "干货新增添加入口",
                description = "文章收集",
                state = STATE_UNDO
            ),
            Todo(
                id = 11,
                title = "待办新增添加入口",
                description = "待办",
                state = STATE_UNDO
            ),
            Todo(
                id = 12,
                title = "页面开发和整合天气模块",
                description = "我",
                state = STATE_DOING
            ),
        )
        return todos.groupBy { it.state }.toSortedMap()
    }
}