package com.tustar.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class Draw4PointView @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
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

        paint.strokeWidth = 40F
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawPoint(250F, 300F, paint)

        paint.strokeCap = Paint.Cap.SQUARE
        canvas.drawPoint(550F, 300F, paint)

        paint.strokeCap = Paint.Cap.BUTT
        canvas.drawPoint(850F, 300F, paint)
    }
}