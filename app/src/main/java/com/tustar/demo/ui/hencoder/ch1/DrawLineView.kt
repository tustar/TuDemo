package com.tustar.demo.ui.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawLineView @JvmOverloads constructor(context: Context,
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

        paint.style = Paint.Style.FILL
        paint.strokeWidth = 3F
        canvas.drawLine(100F, 100F, 500F, 500F, paint)
    }
}