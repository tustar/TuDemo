package com.tustar.demo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.Transformation
import android.widget.ListView
import com.tustar.demo.R

@SuppressLint("NewApi")
class QQListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ListView(context, attrs, defStyleAttr, defStyleRes) {

    lateinit var zoomView: View
    private val zoomHeaderHeight = context.resources.getDimensionPixelSize(
        R.dimen.custom_qq_header_image_height
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        resizeZoomView()
        super.onSizeChanged(w, h, oldw, oldh)
    }

    private fun resizeZoomView() {
        val parent = zoomView.parent as View
        if (zoomView.height > zoomHeaderHeight) {
            zoomView.layoutParams.height = zoomView.height + parent.top
            parent.layout(parent.left, 0, parent.right, parent.height)
            zoomView.requestLayout()
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        handleRollback(ev)
        return super.onTouchEvent(ev)
    }

    private fun handleRollback(ev: MotionEvent) {
        when (ev.action) {
            MotionEvent.ACTION_UP -> {
                val animation = object : Animation() {

                    val currentHeight = zoomView.height
                    val extraHeight = zoomView.height - zoomHeaderHeight

                    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                        zoomView.layoutParams.height = (currentHeight -
                                extraHeight * interpolatedTime).toInt()
                        zoomView.requestLayout()
                        super.applyTransformation(interpolatedTime, t)
                    }
                }.apply {
                    interpolator = OvershootInterpolator()
                    duration = 500
                }
                zoomView.startAnimation(animation)
            }
        }
    }

    override fun overScrollBy(
        deltaX: Int,
        deltaY: Int,
        scrollX: Int,
        scrollY: Int,
        scrollRangeX: Int,
        scrollRangeY: Int,
        maxOverScrollX: Int,
        maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean {
        zoomView.layoutParams.height = zoomView.height - deltaY
        zoomView.requestLayout()
        return super.overScrollBy(
            deltaX,
            deltaY,
            scrollX,
            scrollY,
            scrollRangeX,
            scrollRangeY,
            maxOverScrollX,
            maxOverScrollY,
            isTouchEvent
        )
    }
}