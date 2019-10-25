package com.tustar.demo.ui.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View

class DrawColorView @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet,
                                              defStyleAttr: Int = 0,
                                              defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.YELLOW)
    }
}