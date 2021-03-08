package com.tustar.demo.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
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
    private lateinit var childrenRect: ArrayList<Rect>
    private var itemCount: Int = 0
    private var listitemResId: Int = -1

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0)
            .use {
                horizontalSpacing = it.getDimensionPixelOffset(
                    R.styleable.FlowLayout_android_horizontalSpacing,
                    10
                )
                verticalSpacing = it.getDimensionPixelOffset(
                    R.styleable.FlowLayout_android_verticalSpacing,
                    10
                )
                if (isInEditMode) {
                    itemCount = it.getInt(R.styleable.FlowLayout_itemCount, 0)
                    listitemResId = it.getResourceId(R.styleable.FlowLayout_listitem, -1)
                    if (itemCount > 0 && listitemResId != -1) {
                        for (i in 0 until itemCount) {
                            val child = LayoutInflater.from(context).inflate(
                                listitemResId,
                                this, false
                            )
                            addView(child)
                        }
                    }
                }
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        childrenRect = arrayListOf()
        //
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)
        //
        var rowUsedWidth = 0
        var rowHeight = 0
        var parentNeedWidth = horizontalSpacing
        var parentNeedHeight = verticalSpacing
        // Rect
        var childLeft = paddingLeft + horizontalSpacing
        var childTop = paddingTop + verticalSpacing
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
            if (rowUsedWidth + childMeasureWidth + horizontalSpacing >= selfWidth) {
                // 布局参数
                childTop += rowHeight + verticalSpacing
                childLeft = paddingLeft + horizontalSpacing

                // 换行，记录父布局所需的宽高
                parentNeedWidth = max(parentNeedWidth, rowUsedWidth + horizontalSpacing)
                parentNeedHeight += rowHeight + verticalSpacing

                // 重置行信息
                rowUsedWidth = 0
                rowHeight = 0
            }

            // 子View是分行layout, 要记录每一行的View, 用于布局
            rowUsedWidth += childMeasureWidth + horizontalSpacing
            rowHeight = max(rowHeight, childMeasureHeight)
            // 布局信息
            val left = childLeft
            val top = childTop
            val right = left + childMeasureWidth
            val bottom = top + childMeasureHeight
            childrenRect.add(Rect(left, top, right, bottom))
            childLeft = right + horizontalSpacing

            // 处理最后一行
            if (i == childCount - 1) {
                // 换行，记录父布局所需的宽高
                parentNeedWidth = max(parentNeedWidth, rowUsedWidth + horizontalSpacing)
                parentNeedHeight += rowHeight + verticalSpacing
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
            "size = ${childrenRect.size}, selfWidth=$selfWidth, parentNeedWidth=$parentNeedWidth"
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val rect = childrenRect[i]
            childView.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    fun <T : Adapter<ViewHolder>> setAdapter(adapter: T) {
        // 移除之前的视图
        removeAllViews()
        for (i in 0 until adapter.getItemCount()) {
            val holder: ViewHolder = adapter.onCreateViewHolder(this)
            adapter.onBindViewHolder(holder, i)
            val child = holder.itemView
            addView(child)
        }
    }


    abstract class Adapter<VH : ViewHolder> {
        abstract fun onCreateViewHolder(parent: ViewGroup): VH
        abstract fun onBindViewHolder(holder: VH, position: Int)
        abstract fun getItemCount(): Int
    }

    abstract class ViewHolder(val itemView: View)
}