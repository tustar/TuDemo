package com.tustar.demo.ui.custom

import android.annotation.SuppressLint
import android.graphics.*
import androidx.core.graphics.toRegion

data class ProvinceItem(
    val path: Path,
    val drawColor: Int,
    var selected: Boolean = false
) {
    var bounds: RectF = RectF()

    init {
        path.computeBounds(bounds, true)
    }

    @SuppressLint("NewApi")
    fun draw(canvas: Canvas, paint: Paint) {
        if (selected) {
            // 画边界
            paint.apply {
                clearShadowLayer()
                strokeWidth = 1F
                color = drawColor
                style = Paint.Style.FILL
            }
            canvas.drawPath(path, paint)
            // 填充
            paint.apply {
                clearShadowLayer()
                color = Color.GREEN
                style = Paint.Style.STROKE
            }
            canvas.drawPath(path, paint)
        } else {// 画边界
            paint.apply {
                strokeWidth = 2F
                color = Color.BLACK
                style = Paint.Style.FILL
                setShadowLayer(8F, 0F, 0F, 0xFFF)
            }
            canvas.drawPath(path, paint)
            // 填充
            paint.apply {
                clearShadowLayer()
                color = drawColor
            }
            canvas.drawPath(path, paint)
        }
    }

    fun isTouched(x: Float, y: Float): Boolean {
        val region = Region()
        region.setPath(path, bounds.toRegion())
        return region.contains(x.toInt(), y.toInt())
    }
}