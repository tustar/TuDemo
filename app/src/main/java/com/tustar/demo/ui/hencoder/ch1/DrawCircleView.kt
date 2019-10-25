package com.tustar.demo.ui.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawCircleView @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet,
                                               defStyleAttr: Int = 0,
                                               defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    var paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLACK
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(150F, 300F, 100F, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3F
        canvas.drawCircle(400F, 300F, 100F, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#4A90E2")
        canvas.drawCircle(650F, 300F, 100F, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20F
        paint.color = Color.BLACK
        canvas.drawCircle(900F, 300F, 100F, paint)
    }
}