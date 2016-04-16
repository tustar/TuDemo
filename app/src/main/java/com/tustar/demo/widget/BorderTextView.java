package com.tustar.demo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tustar on 4/16/16.
 */
public class BorderTextView extends TextView {

    private Paint mOuterPaint;
    private Paint mInnerPaint;

    public BorderTextView(Context context) {
        super(context);

        init();
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private void init() {

        mOuterPaint = new Paint();
        mOuterPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mOuterPaint.setStyle(Paint.Style.FILL);

        mInnerPaint = new Paint();
        mInnerPaint.setColor(Color.YELLOW);
        mInnerPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0,0, getMeasuredWidth(), getMeasuredHeight(), mOuterPaint);
        canvas.drawRect(10, 10,getMeasuredWidth() - 10, getMeasuredHeight() - 10, mInnerPaint);
        canvas.save();
        super.onDraw(canvas);
        canvas.restore();
    }
}
