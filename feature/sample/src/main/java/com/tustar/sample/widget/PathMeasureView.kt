package com.tustar.sample.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

@SuppressLint("NewApi")
class PathMeasureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var paint = Paint()
    private var path = Path()
    private var dst = Path()
    private var pathMeasure: PathMeasure
    private var length: Float
    private var valueAnimator: Float = 0.0F
    private val pos = FloatArray(2)

    init {
        paint.apply {
            isAntiAlias = true
            color = Color.CYAN
            style = Paint.Style.STROKE
            strokeWidth = 5.0F
        }
        path.addCircle(400.0F, 400.0F, 100.0F, Path.Direction.CW)
        pathMeasure = PathMeasure(path, true)
        length = pathMeasure.length
        ValueAnimator.ofFloat(0.0F, 1.0F).apply {
            addUpdateListener {
                valueAnimator = it.animatedValue as Float
                invalidate()
            }
            duration = 2000
            repeatCount = ValueAnimator.INFINITE
        }.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        dst.reset()
        dst.lineTo(0.0F, 0.0F)
        val stopD = length * valueAnimator
        pathMeasure.getSegment(0.0F, stopD, dst, true)
        pathMeasure.getPosTan(stopD, pos, null)
        canvas.drawPath(path, paint)
        canvas.drawCircle(pos[0], pos[1], 25.0F, paint)
    }
}