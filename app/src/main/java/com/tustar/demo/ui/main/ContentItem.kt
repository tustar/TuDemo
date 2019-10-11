package com.tustar.demo.ui.main

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes

data class ContentItem(val clazz: Class<*>,
                       @StringRes val descResId: Int,
                       val isMenu: Boolean = false) : MainItem() {
    override fun getType(): Int = TYPE_CONTENT

    fun startActivity(context: Context) {
        val intent = Intent().apply {
            setClass(context, clazz)
        }
        context.startActivity(intent)
    }
}