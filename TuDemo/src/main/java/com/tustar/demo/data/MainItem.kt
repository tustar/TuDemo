package com.tustar.demo.data

import java.util.*

sealed class MainItem {
    abstract fun getType(): Int

    companion object {
        const val TYPE_GROUP = 0
        const val TYPE_CHILD = 1
    }
}

data class Group(val id: Int,
                 val name: Int,
                 val createdAt: Calendar = Calendar.getInstance()) : MainItem() {
    override fun getType(): Int {
        return TYPE_GROUP
    }
}

data class Demo(var id: Int = 0,
                val name: Int,
                var groupId: Int = 0,
                var parentId: Int = -1,
                var actionId: Int,
                val isMenu: Boolean = false,
                val createdAt: Calendar = Calendar.getInstance()) : MainItem() {

    override fun getType(): Int {
        return TYPE_CHILD
    }
}