package com.tustar.demo.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;

import com.tustar.util.Logger;

/**
 * Created by tustar on 11/19/16.
 */

public class OnePointerDrawView extends View {

    private static final String TAG = "OnePointerDrawView";

    private int mLastX;
    private int mLastY;

    private int mTouchSlop;

    private boolean mIsBeingDragged = false;

    private Paint mPaint;

    public OnePointerDrawView(Context context) {
        this(context, null);
    }

    public OnePointerDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnePointerDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OnePointerDrawView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mPaint = new Paint(Color.CYAN);
        Logger.d(TAG, "init :: mTouchSlop = " + mTouchSlop);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        Rect rectMiddle = new Rect(0, 0, width, height);
        canvas.drawRect(rectMiddle, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionId = event.getActionMasked();
        switch (actionId) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mIsBeingDragged = true;
                final ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final int x = (int) event.getX();
                final int y = (int) event.getY();
                int deltaX = mLastX - x;
                int deltaY = mLastY - y;
                Logger.d(TAG, "onTouchEvent :: deltaX = " + deltaX + ", deltaY = " + deltaY);
                if (!mIsBeingDragged && (Math.abs(deltaX) > mTouchSlop
                        || Math.abs(deltaY) > mTouchSlop)) {
                    mIsBeingDragged = true;
                    // 让第一次滑动的距离和之后的距离不至于差距太大
                    // 因为第一次必须>TouchSlop,之后则是直接滑动
                    if (deltaX > 0) {
                        deltaX -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }
                    if (deltaY > 0) {
                        deltaY -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }
                }
                // 当mIsBeingDragged为true时，就不用判断> touchSlopg啦，不然会导致滚动是一段一段的
                // 不是很连续
                if (mIsBeingDragged) {
                    scrollBy(deltaX, deltaY);
                    mLastX = x;
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mLastX = 0;
                mLastY = 0;
                break;
            default:
                break;

        }
        return mIsBeingDragged;
    }
}
