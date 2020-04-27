package com.tustar.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class Draw9PathView @JvmOverloads constructor(context: Context,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0,
                                              defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    var paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val path = Path()
        path.addArc(200F, 200F, 400F, 400F, -225F, 225F)
        path.arcTo(400F, 200F, 600F, 400F, -180F, 225F,
                false)
        path.lineTo(400F, 542F)
        canvas.drawPath(path, paint)
    }
}