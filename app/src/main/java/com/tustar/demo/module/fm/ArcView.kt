package com.tustar.demo.module.fm

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.tustar.common.util.Logger
import com.tustar.demo.R

class ArcView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint
    private var mArcColor = DEFAULT_ARC_COLOR
    private var mArcHeight = DEFAULT_ARC_HEIGHT

    private var mPath: Path
    private var mStartPoint: PointF
    private var mEndPoint: PointF
    private var mControlPoint: PointF

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    private var mStartY0 = 0.0F
    private var mControlY0 = 0.0F
    private var mStartY = 0.0F
    private var mControlY = 0.0F

    init {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.ArcView,
                0, 0)
        try {
            mArcColor = ta.getColor(R.styleable.ArcView_arc_color, DEFAULT_ARC_COLOR)
            mArcHeight = ta.getDimension(R.styleable.ArcView_arc_height, DEFAULT_ARC_HEIGHT)
        } finally {
            ta.recycle()
        }

        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
        mPaint.color = mArcColor
        mPaint.strokeWidth = 10f

        mPath = Path()
        mStartPoint = PointF(0f, 0f)
        mEndPoint = PointF(0f, 0f)
        mControlPoint = PointF(0f, 0f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mWidth = w
        mHeight = h
        mStartY0 = mHeight - mArcHeight
        mControlY0 = mHeight + mArcHeight

        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.addRect(0f, 0f, mWidth.toFloat(), mHeight - mArcHeight, Path.Direction.CCW)

        mStartPoint.x = 0f
        mStartPoint.y = mHeight - mArcHeight

        mEndPoint.x = mWidth.toFloat()
        mEndPoint.y = mHeight - mArcHeight

        mControlPoint.x = (mWidth / 2).toFloat()
        mControlPoint.y = mHeight + mArcHeight

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        Logger.i(TAG, "onDraw")
        super.onDraw(canvas)
        Logger.d(TAG, "Start(${mStartPoint.x}, ${mStartPoint.y}), " +
                "End(${mEndPoint.x}, ${mEndPoint.y}), " +
                "Control(${mControlPoint.x}, ${mControlPoint.y})")
        mPath.moveTo(mStartPoint.x, mStartPoint.y)
        mPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y)
        canvas.drawPath(mPath, mPaint)
    }

    private fun updatePath() {
        // Draw Rect
        mPath.reset()
        mPath.moveTo(0f, 0f)
        mPath.addRect(0f, 0f, mWidth.toFloat(), mStartY, Path.Direction.CCW)

        // Draw Arc
        mStartPoint.x = 0f
        mStartPoint.y = mStartY

        mEndPoint.x = mWidth.toFloat()
        mEndPoint.y = mStartY

        mControlPoint.x = (mWidth / 2).toFloat()
        mControlPoint.y = mControlY
    }

    fun startAnimation(reverse: Boolean = false) {
        val animator =
                ValueAnimator.ofFloat(0.0F, mArcHeight)
        animator.duration = 2 * 1000
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val dx = animation.animatedValue as Float
            if (reverse) {
                mStartY = mStartY0 - dx
                mControlY = mControlY0 + dx
            } else {
                mStartY = mStartY0 + dx
                mControlY = mControlY0 - dx
            }
            updatePath()

            postInvalidate()
        }
        animator.addListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
                mStartY = mStartY0
                mControlY = mControlY0
            }

            override fun onAnimationEnd(animation: Animator) {
                mStartY0 = mStartY
                mControlY0 = mControlY
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

            override fun onAnimationCancel(animation: Animator) {
            }
        })
        animator.start()
    }


    companion object {
        private const val DEFAULT_ARC_COLOR = 0xF0F0F0
        private const val DEFAULT_ARC_HEIGHT = 53.0F
        private const val TAG = "ArcView"
    }
}