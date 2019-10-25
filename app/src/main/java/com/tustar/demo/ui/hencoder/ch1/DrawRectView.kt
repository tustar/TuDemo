package com.tustar.demo.ui.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DrawRectView @JvmOverloads constructor(context: Context,
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

        canvas.drawRect(100F, 150F, 400F, 450F, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3F
        canvas.drawRect(500F, 150F, 800F, 450F, paint)
    }
}