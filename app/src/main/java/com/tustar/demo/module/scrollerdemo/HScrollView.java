package com.tustar.demo.module.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.tustar.demo.util.Logger;

/**
 * Created by tustar on 11/20/16.
 */

public class HScrollView extends LinearLayout {

    private static final String TAG = "HScrollView";
    private static final int INVALID_POINTER = -1;

    private int mActivePointerId = INVALID_POINTER;
    private int mLastY;

    //
    private int mSecondaryPointerId = INVALID_POINTER;
    private int mSecondaryLastY;

    //
    private int mTouchSlop;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;

    //
    private boolean mIsBeingDragged = false;

    //
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;


    public HScrollView(Context context) {
        this(context, null);
    }

    public HScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    private void init(Context context) {

        final ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();
        mMaxFlingVelocity = config.getScaledMaximumFlingVelocity();
        mMinFlingVelocity = config.getScaledMinimumFlingVelocity();
        Logger.d(TAG, "init :: mTouchSlop = " + mTouchSlop);
        Logger.d(TAG, "init :: mMaxFlingVelocity = " + mMaxFlingVelocity);
        Logger.d(TAG, "init :: mMinFlingVelocity = " + mMinFlingVelocity);

        //
        mScroller = new Scroller(context);
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void fling(int velocityY) {
        if (mScroller == null) {
            return;
        }

        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, -500, 10000);
        invalidate();
    }

    private void endDrag() {
        mIsBeingDragged = false;
        recycleVelocityTracker();
        mActivePointerId = INVALID_POINTER;
        mLastY = 0;
    }


    private void requestParentDisallowInterceptTouchEvent() {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {

        final int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                int actionIndex = event.getActionIndex();
                mActivePointerId = event.getPointerId(actionIndex);
                mLastY = (int) event.getY(mActivePointerId);
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(event);
                //分两种情况，一种是初始动作，一个是界面正在滚动，down触摸停止滚动
                mIsBeingDragged = mScroller.isFinished();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionIndex = event.getActionIndex();
                mSecondaryPointerId = event.getPointerId(actionIndex);
                mSecondaryLastY = (int) event.getY(mSecondaryPointerId);
                break;
            case MotionEvent.ACTION_MOVE:
                int activePointerId = event.findPointerIndex(mActivePointerId);
                final int y = (int) event.getY(activePointerId);
                final int yDiff = Math.abs(mLastY - y);
                if (yDiff > mTouchSlop) {
                    mIsBeingDragged = true;
                    mLastY = y;
                    initVelocityTrackerIfNotExists();
                    mVelocityTracker.addMovement(event);

                    requestParentDisallowInterceptTouchEvent();
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                if (pointerId == mActivePointerId) {
                    mActivePointerId = mSecondaryPointerId;
                    mLastY = mSecondaryLastY;
                    mVelocityTracker.clear();
                } else {
                    mSecondaryPointerId = INVALID_POINTER;
                    mSecondaryLastY = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                endDrag();
                break;
            default:
                break;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mScroller == null) {
            return false;
        }

        initVelocityTrackerIfNotExists();

        final int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDragged = mScroller.isFinished();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int actionIndex = event.getActionIndex();
                mActivePointerId = event.findPointerIndex(actionIndex);
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    break;
                }

                int activePointerId = event.findPointerIndex(mActivePointerId);
                if (activePointerId == INVALID_POINTER) {
                    break;
                }

                int y = (int) event.getY(activePointerId);
                int deltaY = mLastY - y;
                if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
                    requestParentDisallowInterceptTouchEvent();
                    mIsBeingDragged = true;
                    if (deltaY > 0) {
                        deltaY -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }
                }
                if (mIsBeingDragged) {
                    scrollBy(0, deltaY);
                    mLastY = y;
                }
                // Secondary
                if (mSecondaryPointerId == INVALID_POINTER) {
                    break;
                }

                activePointerId = event.findPointerIndex(mSecondaryPointerId);
                mSecondaryLastY = (int) event.getY(activePointerId);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                activePointerId = event.findPointerIndex(actionIndex);
                if (mActivePointerId != activePointerId) {
                    mActivePointerId = activePointerId;
                    mLastY = mSecondaryLastY;
                }
                mSecondaryPointerId = INVALID_POINTER;
                mSecondaryLastY = 0;
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                    int velocity = (int) mVelocityTracker.getYVelocity(mActivePointerId);
                    if (Math.abs(velocity) > mMinFlingVelocity) {
                        fling(-velocity);
                    }
                    endDrag();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                endDrag();
                break;
            default:
                break;
        }

        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }

        return true;
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
                maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
