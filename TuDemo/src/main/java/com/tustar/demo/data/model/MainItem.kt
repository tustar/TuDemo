package com.tustar.demo.data.model

import java.util.*

sealed class MainItem {
    abstract fun getType(): Int

    companion object {
        const val TYPE_GROUP = 0
        const val TYPE_CHILD = 1
    }
}

data class GroupItem(
    var group: Int = 0,
    val createdAt: Calendar = Calendar.getInstance()
) : MainItem() {
    override fun getType(): Int {
        return TYPE_GROUP
    }
}

data class DemoItem(
    var group: Int = 0,
    val item: Int = 0,
    val createdAt : String,
    val updatedAt : String,
) : MainItem() {

    override fun getType(): Int {
        return TYPE_CHILD
    }
}