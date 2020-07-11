package com.tustar.demo.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.EdgeEffect;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * Created by tustar on 11/20/16.
 */

public class HOverScrollView extends LinearLayout {

    private static final int INVALID_POINTER = -1;

    private int mActivePointerId = INVALID_POINTER;
    private int mLastY;

    //
    private int mSecondaryPointerId = INVALID_POINTER;
    private int mSecondaryLastY;

    //
    private int mTouchSlop;
    private int mMaxFlingVelocity;
    private int mMinFlnggVelocity;
    private int mOverflingDistance;
    private int mOverscrollDistance;

    //
    private boolean mIsBeingDragged = false;

    //
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;

    //
    private EdgeEffect mEdgeGlowTop;
    private EdgeEffect mEdgeGlowBottom;

    public HOverScrollView(Context context) {
        this(context, null);
    }

    public HOverScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HOverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HOverScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context);
    }

    private void init(Context context) {

        final ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();
        mMaxFlingVelocity = config.getScaledMaximumFlingVelocity();
        mMinFlnggVelocity = config.getScaledMinimumFlingVelocity();
        mOverflingDistance = config.getScaledOverflingDistance();
        mOverscrollDistance = config.getScaledOverscrollDistance();

        //
        mScroller = new OverScroller(context);

        //
        mEdgeGlowTop = new EdgeEffect(context);
        mEdgeGlowBottom = new EdgeEffect(context);

        //一般来说mOverScrollDistance为0，OverFlingDistance不一致，这里为了整强显示效果
        mOverflingDistance = 50;
        setOverScrollMode(OVER_SCROLL_ALWAYS);
        // 这里还是需要的。overScrollBy中会使用到
        /**
         * Because by default a layout does not need to draw,
         * so an optimization is to not call is draw method. By calling setWillNotDraw(
         * false) you tell the UI toolkit that you want to draw
         */
        setWillNotDraw(false); // 必须！！！
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

    private int getScrollRange() {
        int scrollRange = 0;
        final int childCount = getChildCount();
        if (childCount > 0) {
            int totalHeight = 0;
            for (int i = 0; i < childCount; i++) {
                totalHeight += getChildAt(i).getHeight();
                //先假设没有margin的情况
            }
            scrollRange = Math.max(0, totalHeight - getHeight());
        }
        return scrollRange;
    }

    private void fling(int velocityY) {
        if (mScroller == null) {
            return;
        }
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, -500, 10000);
        invalidate();
    }

    private void requestParentDisallowInterceptTouchEvent() {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void endDrag() {
        mIsBeingDragged = false;
        mActivePointerId = INVALID_POINTER;
        mLastY = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mEdgeGlowTop != null) {
            final int scrollY = getScrollY();
            if (!mEdgeGlowTop.isFinished()) {
                final int count = canvas.save();
                final int width = getWidth() - getPaddingLeft() - getPaddingRight();
                canvas.translate(getPaddingLeft(), Math.min(0, scrollY));
                mEdgeGlowTop.setSize(width, getHeight());
                if (mEdgeGlowTop.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(count);
            }
        }

        if (mEdgeGlowBottom != null) {
            final int scrollY = getScrollY();
            if (!mEdgeGlowBottom.isFinished()) {
                final int count = canvas.getSaveCount();
                final int width = getWidth() - getPaddingLeft() - getPaddingRight();
                canvas.translate(-width + getPaddingLeft(),
                        Math.max(getScrollRange(), scrollY) + getHeight());
                canvas.rotate(180, width, 0);
                mEdgeGlowBottom.setSize(width, getHeight());
                if (mEdgeGlowBottom.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(count);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                int actionIndex = event.getActionIndex();
                mActivePointerId = event.getPointerId(actionIndex);
                initVelocityTrackerIfNotExists();
                mVelocityTracker.addMovement(event);
                mLastY = (int) event.getY(mActivePointerId);
                //分两种情况，一种是初始动作，一个是界面正在滚动，down触摸停止滚动
                mIsBeingDragged = mScroller.isFinished();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionIndex = event.getActionIndex();
                mSecondaryPointerId = event.getPointerId(actionIndex);
                mSecondaryLastY = (int) event.getY(mSecondaryPointerId);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(mActivePointerId);
                final int y = (int) event.getY(pointerIndex);
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
                pointerIndex = event.getPointerId(actionIndex);
                if (pointerIndex == mActivePointerId) {
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
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                recycleVelocityTracker();
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

                int pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex == INVALID_POINTER) {
                    break;
                }

                int x = (int) event.getX(pointerIndex);
                int y = (int) event.getY(pointerIndex);
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
                    overScrollBy(0, deltaY, 0, getScrollY(), 0, getScrollRange(), 0,
                            mOverscrollDistance, true);
                    mLastY = y;
                    // EdgeEffect
                    final int pulledToY = getScrollY() + deltaY;
                    if (pulledToY < 0) {
                        mEdgeGlowTop.onPull(deltaY / getHeight(), x / getWidth());
                        if (!mEdgeGlowBottom.isFinished()) {
                            mEdgeGlowBottom.onRelease();
                        }
                    } else if (pulledToY > getScrollRange()) {
                        mEdgeGlowBottom.onPull(deltaY / getHeight(), 1.0f - x / getWidth());
                        if (!mEdgeGlowTop.isFinished()) {
                            mEdgeGlowTop.onRelease();
                        }
                    }

                    if (mEdgeGlowTop != null && mEdgeGlowBottom != null
                            && (!mEdgeGlowTop.isFinished() || !mEdgeGlowBottom.isFinished())) {
                        postInvalidate();
                    }
                }
                // Secondary
                if (mSecondaryPointerId == INVALID_POINTER) {
                    break;
                }

                pointerIndex = event.findPointerIndex(mSecondaryPointerId);
                mSecondaryLastY = (int) event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                pointerIndex = event.findPointerIndex(actionIndex);
                if (mActivePointerId != pointerIndex) {
                    mActivePointerId = pointerIndex;
                    mLastY = mSecondaryLastY;
                }
                mSecondaryPointerId = INVALID_POINTER;
                mSecondaryLastY = 0;
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                    int velocityY = (int) mVelocityTracker.getYVelocity(mActivePointerId);
                    if (Math.abs(velocityY) > mMinFlnggVelocity) {
                        fling(-velocityY);
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
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (!mScroller.isFinished()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            scrollTo(oldX, oldY);
            onScrollChanged(scrollX, scrollY, oldX, oldY);
            if (clampedY) {
                mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange());
            }
        } else {
            super.scrollTo(scrollX, scrollY);
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            int range = getScrollRange();
            if (oldX != x || oldY != y) {
                overScrollBy(x - oldX, y - oldY, oldX, oldY,
                        0, range, 0, mOverscrollDistance, false);
            }
            final int overScrollMode = getOverScrollMode();
            final boolean canOverScroll = overScrollMode == OVER_SCROLL_ALWAYS ||
                    (overScrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);
            if (canOverScroll) {
                final int velocityY = (int) mScroller.getCurrVelocity();
                if (y < 0 && oldY > 0) {
                    mEdgeGlowTop.onAbsorb(velocityY);
                } else if (y > range && oldY < range) {
                    mEdgeGlowBottom.onAbsorb(velocityY);
                }
            }
        }
        super.computeScroll();
    }
}
