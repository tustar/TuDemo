package com.tustar.demo.ui.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.text.NumberFormat

class PieChart11View @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
                                               defStyleAttr: Int = 0,
                                               defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {


    val paint = Paint()
    var sortedData: Map<String, Float>

    init {
        paint.isAntiAlias = true
        sortedData = DATAS.mapValues { NumberFormat.getPercentInstance().parse(it.value).toFloat() }
                .toList()
                .sortedBy { it.second }
                .asReversed()
                .toMap()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val all = RectF(340F, 120F, 740F, 520F)
        val first = RectF(335F, 115F, 735F, 515F)
        var startAngle = -180F
        sortedData.values.forEachIndexed { index, fl ->
            val sweepAngle = fl * 360F
            paint.color = COLORS[index]
            var oval = all
            if(index == 0) {
                oval = first
            }
            canvas.drawArc(oval, startAngle, sweepAngle, true, paint)
            startAngle += sweepAngle
        }


    }

    companion object {
        val DATAS = mapOf(
                "Gingerbread" to "0.3%",
                "Ice Cream Sandwich" to "0.3%",
                "Jelly Bean" to "3.2%",
                "KitKat" to "6.9%",
                "Lollipop" to "14.5%",
                "Marshmallow" to "16.9%",
                "Nougat" to "19.2%",
                "Oreo" to "28.3%",
                "Pie" to "10.4%"
        )

        val COLORS = listOf(
                Color.RED,
                Color.YELLOW,
                Color.CYAN,
                Color.parseColor("#00e0e0"),
                Color.MAGENTA,
                Color.BLUE,
                Color.GREEN,
                Color.parseColor("#ff00ddff"),
                Color.parseColor("#ffff8800"))

        const val TAG = "PieChart11View"
    }
}