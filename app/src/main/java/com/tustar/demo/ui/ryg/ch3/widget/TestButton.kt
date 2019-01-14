package com.tustar.demo.ui.ryg.ch3.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.TextView
import com.tustar.common.util.Logger


/**
 * Created by tustar on 17-7-31.
 */
class TestButton : TextView {

    private var mScaledTouchSlop = 0
    private var mLastX = 0
    private var mLastY = 0

    companion object {
        private val TAG = TestButton::class.java.simpleName
    }

    constructor(context: Context?) : super(context) {
        init()
    }


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        mScaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        Logger.d(TAG, "init :: mScaledTouchSlop = $mScaledTouchSlop")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.rawX.toInt()
        var y = event.rawY.toInt()
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX = x - mLastX
                var deltaY = y - mLastY
                val translationX = translationX + deltaX
                val translationY = translationY + deltaY
                setTranslationX(translationX)
                setTranslationY(translationY)
            }
            MotionEvent.ACTION_UP -> {

            }
            else -> {

            }
        }

        mLastX = x
        mLastY = y
        return true
    }
}

