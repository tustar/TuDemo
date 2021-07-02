package com.tustar.demo.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.toRegion
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
            var left = -1.0F
            var top = -1.0F
            var right = -1.0F
            var bottom = -1.0F
            value.forEach {
                val bounds = it.bounds
                left = if (left == -1.0F) {
                    bounds.left
                } else {
                    min(bounds.left, left)
                }
                top = if (top == -1.0F) {
                    bounds.top
                } else {
                    min(bounds.top, top)
                }
                right = if (right == -1.0F) {
                    bounds.right
                } else {
                    max(bounds.right, right)
                }
                bottom = if (bottom == -1.0F) {
                    bounds.bottom
                } else {
                    max(bounds.bottom, bottom)
                }
            }
            totalRectF = RectF(left, top, right, bottom)
            requestLayout()
            postInvalidate()
        }
    private var valueAnimator: Float = 0.0F

    init {
        ValueAnimator.ofFloat(0.0F, 1.0F).apply {
            addUpdateListener {
                valueAnimator = it.animatedValue as Float
                invalidate()
            }
            repeatCount = 1
            duration = 2000
        }.start()
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
        super.onDraw(canvas)
        canvas.save()
        canvas.scale(sx, sy)
        provinces.forEachIndexed { index, province ->
            province.draw(canvas, paint, valueAnimator)
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

data class ProvinceItem(
    val path: Path,
    val drawColor: Int,
    var selected: Boolean = false
) {
    var bounds: RectF = RectF()
    var pathMeasure = PathMeasure(path, true)
    var dst: Path = Path()

    init {
        path.computeBounds(bounds, true)
    }

    @SuppressLint("NewApi")
    fun draw(canvas: Canvas, paint: Paint) {
        if (selected) {
            // 画边界
            paint.apply {
                clearShadowLayer()
                strokeWidth = 1.0F
                color = drawColor
                style = Paint.Style.FILL
            }
            canvas.drawPath(path, paint)
            // 填充
            paint.apply {
                clearShadowLayer()
                color = Color.GREEN
                style = Paint.Style.STROKE
            }
            canvas.drawPath(path, paint)
        } else {// 画边界
            paint.apply {
                strokeWidth = 2.0F
                color = Color.BLACK
                style = Paint.Style.FILL
                setShadowLayer(8F, 0F, 0F, 0xFFF)
            }
            canvas.drawPath(path, paint)
            // 填充
            paint.apply {
                clearShadowLayer()
                color = drawColor
            }
            canvas.drawPath(path, paint)
        }
    }

    @SuppressLint("NewApi")
    fun draw(canvas: Canvas, paint: Paint, progress: Float) {
        paint.apply {
            isAntiAlias = true
            strokeWidth = 1.0F
            color = Color.BLACK
            style = Paint.Style.STROKE
        }
        dst.reset()
        dst.lineTo(0.0F, 0.0F)
        val stopD = pathMeasure.length * progress
        pathMeasure.getSegment(0.0F, stopD, dst, true)
        canvas.drawPath(dst, paint)
    }

    fun isTouched(x: Float, y: Float): Boolean {
        val region = Region()
        region.setPath(path, bounds.toRegion())
        return region.contains(x.toInt(), y.toInt())
    }
}