package com.tustar.weather.util

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color

object BitmapUtils {

    fun getZeroColor(context: Context, resId: Int): Int {
        val bitmap = BitmapFactory.decodeResource(context.resources, resId)
        val pixel = bitmap.getPixel(0, 0)
        val r = Color.red(pixel)
        val g = Color.green(pixel)
        val b = Color.blue(pixel)
        return Color.rgb(r, g, b)
    }
}