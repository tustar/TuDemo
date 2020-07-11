package com.tustar.ryg.ch3.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ListView

/**
 * Created by tustar on 17-8-8.
 */
class ListViewEx : ListView {

    companion object {
        private val TAG = ListViewEx::class.java.simpleName
    }

    private var mLastX = 0
    private var mLastY = 0

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var x = ev.x.toInt()
        var y = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX = x - mLastX
                var deltaY = y - mLastY
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {

            }
            else -> {

            }
        }

        mLastX = x
        mLastY = y
        return super.dispatchTouchEvent(ev)
    }
}