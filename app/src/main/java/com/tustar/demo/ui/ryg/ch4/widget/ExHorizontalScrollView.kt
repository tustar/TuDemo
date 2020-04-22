package com.tustar.demo.ui.ryg.ch3.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import com.tustar.util.Logger

/**
 * Created by tustar on 17-8-1.
 */
class ExHorizontalScrollView : ViewGroup {

    companion object {
        private val TAG = OuterHorizontalScrollView::class.java.simpleName
    }

    private var mChildrenSize = 0
    private var mChildWidth = 0
    private var mChildIndex = 0

    private var mLastX = 0
    private var mLastY = 0

    private var mLastXIntercept = 0
    private var mLastYIntercept = 0

    private var mScroller: Scroller? = null
    private var mVelocityTracker: VelocityTracker? = null

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
        mScroller = Scroller(context)
        mVelocityTracker = VelocityTracker.obtain()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth = 0
        var measuredHeight = 0
        val childCount = childCount
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        var widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        var widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        var heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)

        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(widthSpecSize, measuredHeight)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            setMeasuredDimension(measuredWidth, heightSpecSize)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childCount = childCount
        mChildrenSize = childCount
        for (i in 0..childCount) {
            val childView = getChildAt(i)
            if (childView != null && childView.visibility != View.GONE) {
                val childWidth = childView.measuredWidth
                mChildWidth = childWidth
                childView.layout(childLeft, 0, childLeft + childWidth, childView.measuredHeight)
                childLeft += childWidth
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted = false
        var x = ev.x.toInt()
        var y = ev.y.toInt()
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                intercepted = false
                if (!mScroller!!.isFinished) {
                    mScroller!!.abortAnimation()
                    intercepted = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX = x - mLastXIntercept
                var deltaY = y - mLastYIntercept
                intercepted = Math.abs(deltaX) > Math.abs(deltaY)
            }
            MotionEvent.ACTION_UP -> {
                intercepted = false
            }
            else -> {

            }
        }

        mLastX = x
        mLastY = y
        mLastXIntercept = x
        mLastYIntercept = y
        Logger.d(TAG, "onInterceptTouchEvent :: intercepted = $intercepted")
        return intercepted
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mVelocityTracker!!.addMovement(event)
        var x = event.x.toInt()
        var y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!mScroller!!.isFinished) {
                    mScroller!!.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX = x - mLastX
                scrollBy(-deltaX, 0)
            }
            MotionEvent.ACTION_UP -> {
                var scrollX = scrollX
                mVelocityTracker!!.computeCurrentVelocity(1000)
                var xVelocity = mVelocityTracker!!.xVelocity
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = if (xVelocity > 0) mChildIndex - 1 else mChildIndex + 1
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1))
                var dx = mChildIndex * mChildWidth - scrollX
                smoothScrollBy(dx, 0)
                mVelocityTracker!!.clear()
            }
            else -> {

            }
        }

        mLastX = x
        mLastY = y
        return true
    }

    private fun smoothScrollBy(dx: Int, dy: Int) {
        mScroller!!.startScroll(scrollX, 0, dx, 0, 500)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller!!.computeScrollOffset()) {
            scrollTo(mScroller!!.currX, mScroller!!.currY)
            postInvalidate()
        }
    }

    override fun onDetachedFromWindow() {
        mVelocityTracker!!.recycle()
        super.onDetachedFromWindow()
    }
}