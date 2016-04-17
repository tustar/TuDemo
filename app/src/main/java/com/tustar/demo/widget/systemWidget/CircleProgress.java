package com.tustar.demo.widget.systemWidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tustar on 4/17/16.
 */
public class CircleProgress extends View {

    private int mWidth;
    private int mHeight;

    private Paint mArcPaint;
    private RectF mArcRectF;
    private float mSweepAngle;
    private float mSweepValue = 66;

    private Paint mCirclePaint;
    private float mCircleXY;
    private float mRadius;

    private Paint mTextPaint;
    private String mShowText;
    private float mShowTextSize;

    public CircleProgress(Context context) {
        super(context);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw arc
        canvas.drawArc(mArcRectF, 270, mSweepAngle, false, mArcPaint);

        // Draw circle
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);

        // Draw text
        canvas.drawText(mShowText, 0, mShowText.length(), mCircleXY,
                mCircleXY + mShowTextSize / 4.0f, mTextPaint);
    }

    private void init() {

        int length = Math.min(mWidth, mHeight);

        // Arc
        mArcRectF = new RectF(length * 0.1f, length * 0.1f, length * 0.9f, length * 0.9f);
        mSweepAngle = (mSweepValue / 100) * 360.0f;
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        mArcPaint.setStrokeWidth(length * 0.1f);
        mArcPaint.setStyle(Paint.Style.STROKE);

        // Circle
        mCircleXY = length / 2.0f;
        mRadius = length / 4.0f;
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));

        // Text
        mShowText = setShowText();
        mShowTextSize = setShowTextSize();
        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mShowTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private float setShowTextSize() {
        invalidate();
        return 50;
    }

    private String setShowText() {
        invalidate();
        return "Android Skill";
    }

    public void forceInvalidate() {
        invalidate();
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
            mSweepValue = sweepValue;
        } else {
            mSweepValue = 25;
        }
        invalidate();
    }
}
