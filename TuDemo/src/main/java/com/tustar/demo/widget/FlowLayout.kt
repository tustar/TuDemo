package com.tustar.demo.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.use
import com.tustar.demo.R
import kotlin.math.max

class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var horizontalSpacing = 10
    private var verticalSpacing = 10
    private lateinit var rows: ArrayList<Row>

    init {
        context.obtainStyledAttributes(attrs, R.styleable.FlowLayout).use {
            horizontalSpacing = it.getDimensionPixelOffset(R.styleable.FlowLayout_android_horizontalSpacing,
                10)
            verticalSpacing = it.getDimensionPixelOffset(R.styleable.FlowLayout_android_verticalSpacing,
                10)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        rows = arrayListOf()
        //
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)
        //
        var row = Row()
        var rowUsedWidth = 0
        var parentNeedWidth = horizontalSpacing
        var parentNeedHeight = verticalSpacing
        // Measure children
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val childParams = childView.layoutParams
            val childWidthMeasureSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingLeft + paddingRight, childParams.width
            )
            val childHeightMeasureSpec = getChildMeasureSpec(
                heightMeasureSpec,
                paddingTop + paddingBottom, childParams.height
            )
            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)

            val childMeasureWidth = childView.measuredWidth
            val childMeasureHeight = childView.measuredHeight

            // 通过宽度来判断是否需要换行，通过换行后的每行行高来获取整个ViewGroup的高
            if (rowUsedWidth + childMeasureWidth + horizontalSpacing > selfWidth) {
                // 记录行信息
                rows.add(row)

                // 换行，记录父布局所需的宽高
                parentNeedWidth = max(parentNeedWidth, rowUsedWidth + horizontalSpacing)
                parentNeedHeight += row.height + verticalSpacing

                // 重置行信息
                row = Row()
                rowUsedWidth = 0
            }

            // 子View是分行layout, 要记录每一行的View, 用于布局
            rowUsedWidth += childMeasureWidth + horizontalSpacing
            row.addChild(childView, childMeasureHeight)

            // 处理最后一行
            if (i == childCount - 1 && rowUsedWidth <= selfWidth) {
                // 记录行信息
                rows.add(row)

                // 换行，记录父布局所需的宽高
                parentNeedWidth = max(parentNeedWidth, rowUsedWidth + horizontalSpacing)
                parentNeedHeight += row.height + verticalSpacing
            }
        }

        /**
         * 根据子View的测量结果，来重新测试自己的ViewGroup, 作为一个ViewGroup， 它自己也是一个View,
         * 它的大小也需要根据它的父容器给它提供的宽高来测量
         */
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val realWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> selfWidth
            else -> parentNeedWidth
        }
        val realHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> selfHeight
            else -> parentNeedHeight
        }
        // Measure self
        setMeasuredDimension(realWidth, realHeight)
        Log.d(
            "FlowLayout",
            "size = ${rows.size}, selfWidth=$selfWidth, parentNeedWidth=$parentNeedWidth"
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = paddingLeft + horizontalSpacing
        var rowTop = paddingTop + verticalSpacing

        rows.forEach { row ->
            row.views.forEach { childView ->
                val left = childLeft
                val top = rowTop
                var right = left + childView.measuredWidth
                val bottom = top + childView.measuredHeight
                childView.layout(left, top, right, bottom)
                childLeft = right + horizontalSpacing
            }
            // 布局完一行，重置childLeft
            childLeft = paddingLeft + horizontalSpacing
            rowTop += row.height + verticalSpacing
        }
    }

    data class Row(
        var views: MutableList<View> = mutableListOf<View>(),
        var height: Int = 0
    ) {
        fun addChild(childView: View, childHeight: Int) {
            views.add(childView)
            height = max(height, childHeight)
        }
    }
}