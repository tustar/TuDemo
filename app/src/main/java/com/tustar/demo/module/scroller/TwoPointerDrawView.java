package com.tustar.demo.module.scroller;

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

import com.tustar.demo.util.Logger;

/**
 * Created by tustar on 11/19/16.
 */

public class TwoPointerDrawView extends View {

    private static final String TAG = "OnePointerDrawView";
    private static final int INVALID_POINTER = -1;

    //
    private int mActivePointerId = INVALID_POINTER;
    private int mLastX;
    private int mLastY;

    //
    private int mSecondaryPointerId = INVALID_POINTER;
    private int mSecondaryLastX;
    private int mSecondaryLastY;

    private int mTouchSlop;

    private boolean mIsBeingDragged = false;

    private Paint mPaint;

    public TwoPointerDrawView(Context context) {
        this(context, null);
    }

    public TwoPointerDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoPointerDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TwoPointerDrawView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        // left rect
        canvas.save();
        canvas.translate(-width, 0);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectMiddle, mPaint);
        canvas.restore();

        //right rect
        canvas.save();
        canvas.translate(width, 0);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(rectMiddle, mPaint);
        canvas.restore();

        // top rect
        canvas.save();
        canvas.translate(0, -height);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(rectMiddle, mPaint);
        canvas.restore();

        // bottom rect
        canvas.save();
        canvas.translate(0, height);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectMiddle, mPaint);
        canvas.restore();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                int actionIndex = event.getActionIndex();
                mActivePointerId = event.findPointerIndex(actionIndex);
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mIsBeingDragged = true;
                final ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionIndex = event.getActionIndex();
                mSecondaryPointerId = event.findPointerIndex(actionIndex);
                mSecondaryLastX = (int) event.getX(actionIndex);
                mSecondaryLastY = (int) event.getY(actionIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    break;
                }
                int activePointerIndex = event.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    break;
                }

                int x = (int) event.getX(activePointerIndex);
                int y = (int) event.getY(activePointerIndex);
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
                // handle secondary pointer move
                if (mSecondaryPointerId == INVALID_POINTER) {
                    break;
                }

                int secondaryPointerIndex = event.findPointerIndex(mSecondaryPointerId);
                mSecondaryLastX = (int) event.getX(secondaryPointerIndex);
                mSecondaryLastY = (int) event.getY(secondaryPointerIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                activePointerIndex = event.findPointerIndex(actionIndex);
                if (activePointerIndex == mActivePointerId) {
                    mActivePointerId = mSecondaryPointerId;
                    mLastX = mSecondaryLastX;
                    mLastY = mSecondaryLastY;
                }
                mSecondaryPointerId = INVALID_POINTER;
                mSecondaryLastX = 0;
                mSecondaryLastY = 0;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = 0;
                mLastX = 0;
                mLastY = 0;
                break;
            default:
                break;

        }
        return true;
    }
}
