package com.tustar.demo.ui.main

import androidx.annotation.StringRes
import androidx.navigation.NavDirections

sealed class MainItem() {

    abstract fun getType(): Int

    companion object {
        const val TYPE_SECTION = 0
        const val TYPE_CONTENT = 1
    }
}

data class GroupItem(@StringRes val nameResId: Int,
                     val childCount: Int) : MainItem() {

    override fun getType(): Int = TYPE_SECTION
}

data class ChildItem(@StringRes val nameResId: Int,
                     val direction: NavDirections,
                     val isMenu: Boolean = false) : MainItem() {
    override fun getType(): Int = TYPE_CONTENT
}