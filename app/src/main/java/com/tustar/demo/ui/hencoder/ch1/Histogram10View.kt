package com.tustar.demo.ui.hencoder.ch1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.text.NumberFormat

class Histogram10View @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyleAttr: Int = 0,
                                                defStyleRes: Int = 0)
    : View(context, attrs, defStyleAttr, defStyleRes) {

    val linePaint = Paint()
    val colPaint = Paint()
    val textPaint = Paint()
    val colHeight = 400F
    val colWidth = 45F
    val colPadding = 40F
    val textMargin = 20F

    init {

        linePaint.isAntiAlias = true
        linePaint.color = Color.BLACK

        colPaint.isAntiAlias = true
        colPaint.color = Color.BLUE

        textPaint.color = Color.BLACK
        textPaint.isAntiAlias = true
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 28F
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        linePaint.strokeWidth = 3F
        val yStart = 100F
        val yEnd = yStart + colHeight
        val xStart = 100F
        val xEnd = xStart + 900F
        //
        canvas.drawLine(xStart - 10F, yStart + 10F, xStart, yStart, linePaint)
        canvas.drawLine(xStart, yStart, xStart, yEnd, linePaint)
        canvas.drawLine(xStart, yStart, xStart + 10F, yStart + 10F, linePaint)
        //
        canvas.drawLine(xEnd - 10F, yEnd - 10F, xEnd, yEnd, linePaint)
        canvas.drawLine(xStart, yEnd, xEnd, yEnd, linePaint)
        canvas.drawLine(xEnd, yEnd, xEnd - 10F, yEnd + 10F, linePaint)


        var left = xStart
        val bottom = yEnd - 1.5F
        data.forEach { (t, u) ->
            val percent = NumberFormat.getPercentInstance().parse(u).toFloat()
            left += colPadding
            val top = bottom - colHeight * percent
            val right = left + colWidth
            canvas.drawRect(left, top, right, bottom, colPaint)

            // Draw percent
            val percentX = left + colWidth / 2
            val percentY = top - textMargin
            canvas.drawText(u, percentX, percentY, textPaint)

            // Draw os text
            val osX = left + colWidth / 2
            val osY = yEnd + textMargin * 2
            canvas.drawText(t, 0, 1, osX, osY, textPaint)

            left += colWidth
        }
    }

    companion object {
        val data = mapOf(
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

        const val TAG = "Histogram10View"
    }
}