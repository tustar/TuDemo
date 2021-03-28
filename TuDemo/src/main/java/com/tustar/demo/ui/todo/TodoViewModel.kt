package com.tustar.demo.ui.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {
    fun getTodos(): Map<Int, List<Todo>> {
        val todos = listOf(
            Todo(
                id = 1,
                title = "MotionLayout",
                description = "MotionLayout实战",
                category = "Android",
                state = STATE_DONE
            ),
            Todo(
                id = 2,
                title = "Hilt",
                description = "Hilt实战",
                category = "Android",
                state = STATE_DONE
            ),
            Todo(
                id = 3,
                title = "Compose 布局",
                description = "Jetpack Compose实战",
                category = "Jetpack compose",
                state = STATE_DOING
            ),
            Todo(
                id = 4,
                title = "Compose 列表",
                description = "Jetpack Compose实战",
                category = "Jetpack compose",
                state = STATE_DOING
            ),
            Todo(
                id = 5,
                title = "码农世界Compose重构",
                description = "Jetpack Compose实战",
                category = "Jetpack compose",
                state = STATE_DOING
            ),
            Todo(
                id = 6,
                title = "Compose+Navigation",
                description = "Jetpack Compose实战",
                category = "Jetpack compose",
                state = STATE_UNDO
            ),
            Todo(
                id = 7,
                title = "干货文章整理成Json数据",
                description = "文章收集",
                category = "干货",
                state = STATE_UNDO
            ),
            Todo(
                id = 8,
                title = "干货新增分享收集入口和出口",
                description = "文章收集",
                category = "干货",
                state = STATE_UNDO
            ),
            Todo(
                id = 9,
                title = "干货新增浏览器阅读",
                description = "文章收集",
                category = "干货",
                state = STATE_UNDO
            ),
            Todo(
                id = 10,
                title = "干货新增添加入口",
                description = "文章收集",
                category = "干货",
                state = STATE_UNDO
            ),
            Todo(
                id = 11,
                title = "待办新增添加入口",
                description = "待办",
                category = "待办",
                state = STATE_UNDO
            ),
            Todo(
                id = 12,
                title = "页面开发和整合天气模块",
                description = "我",
                category = "待办",
                state = STATE_DOING
            ),
            Todo(
                id = 13,
                title = "Python面试题",
                description = "Python",
                category = "面试",
                state = STATE_CANCELED
            ),
            Todo(
                id = 14,
                title = "Go学习",
                description = "Go",
                category = "Go",
                state = STATE_CANCELED
            ),
        )
        return todos.groupBy { it.state }.toSortedMap()
    }
}