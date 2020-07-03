package com.tustar.util

import android.view.View


abstract class NoFastClickListener : View.OnClickListener {

    abstract fun onNoFastClick(v: View)

    override fun onClick(v: View) {
        val currClickTime = System.currentTimeMillis()
        if (currClickTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = currClickTime
            onNoFastClick(v)
        }
    }

    companion object {
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private const val MIN_CLICK_DELAY_TIME = 1000
        private var lastClickTime: Long = 0
    }
}