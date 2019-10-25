package com.tustar.demo.ui.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawRoundRectView @JvmOverloads constructor(context: Context,
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

        canvas.drawRoundRect(100F, 150F, 500F, 450F, 50F, 50F, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3F
        canvas.drawRoundRect(600F, 150F, 1000F, 450F, 50F, 50F, paint)
    }
}