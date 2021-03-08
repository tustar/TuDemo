package com.tustar.demo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.tustar.demo.ui.custom.ProvinceItem
import java.lang.Float.max
import java.lang.Float.min

@SuppressLint("NewApi")
class SvgMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var sx: Float = 1.0F
    private var sy: Float = 1.0F
    private val paint = Paint().apply {
        isAntiAlias = true
    }
    private var totalRectF: RectF? = null

    var provinces: List<ProvinceItem> = emptyList()
        set(value) {
            field = value
            var left = -1F
            var top = -1F
            var right = -1F
            var bottom = -1F
            value.forEach {
                val bounds = it.bounds
                left = if (left == -1F) {
                    bounds.left
                } else {
                    min(bounds.left, left)
                }
                top = if (top == -1F) {
                    bounds.top
                } else {
                    min(bounds.top, top)
                }
                right = if (right == -1F) {
                    bounds.right
                } else {
                    max(bounds.right, right)
                }
                bottom = if (bottom == -1F) {
                    bounds.bottom
                } else {
                    max(bounds.bottom, bottom)
                }
            }
            totalRectF = RectF(left, top, right, bottom)
            requestLayout()
            postInvalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        totalRectF?.let {
            sx = width / it.width()
            sy = sx
        }
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.scale(sx, sy)
        provinces.forEach {
            it.draw(canvas, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        handleProvinceItemTouch(event)
        return super.onTouchEvent(event)
    }

    private fun handleProvinceItemTouch(event: MotionEvent) {
        val x = event.x / sx
        val y = event.y / sy
        provinces.forEach {
            it.selected = it.isTouched(x, y)
        }
        postInvalidate()
    }
}