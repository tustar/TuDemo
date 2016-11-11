package com.tustar.demo.widget.dragview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.tustar.demo.R;
import com.tustar.demo.util.Logger;

/**
 * Created by tustar on 16-11-11.
 */

public class HistoryLayout extends FrameLayout {

    private static final String TAG = "HistoryLayout";
    public static final int DEFAULT_SCROLLER_DURATION = 200;

    private int mWidth;
    private int mHeight;

    private int mTopViewId = 0;
    private int mContentViewId = 0;
    private View mTopView;
    private View mContentView;

    // Position
    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;

    private ViewDragHelper mViewDragHelper;
    private int mTouchSlop;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mScaledMinimumFlingVelocity;
    private int mScaledMaximumFlingVelocity;

    //
    private boolean mScrollable;
    private boolean mDraggingInVertical;
    private int mScrollerDuration = DEFAULT_SCROLLER_DURATION;

    public HistoryLayout(Context context) {
        this(context, null);
    }

    public HistoryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistoryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HistoryLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d("onMeasure :: ");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Logger.d("onMeasure :: mWidth = " + mWidth + ", mHeight = " + mHeight);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mTopViewId != 0 && mTopView == null) {
            mTopView = findViewById(mTopViewId);
        }
        if (mContentViewId != 0 && mContentView == null) {
            mContentView = findViewById(mContentViewId);
        } else {
            TextView errorView = new TextView(getContext());
            errorView.setClickable(true);
            errorView.setGravity(Gravity.CENTER);
            errorView.setTextSize(16);
            errorView.setText("No content view");
            mContentView = errorView;
            addView(mContentView);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (mContentView != null) {
            int contentViewWidth = mContentView.getMeasuredWidthAndState();
            int contentViewHeight = mContentView.getMeasuredHeightAndState();
            LayoutParams params = (LayoutParams) mContentView.getLayoutParams();
            int l = mContentView.getPaddingLeft();
            int t = mContentView.

        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = mLastX = (int) event.getX();
                mDownY = mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 不可滑动
                if (!mScrollable) {
                    break;
                }
                int dx = (int) (mLastX - event.getX());
                int dy = (int) (mLastY - event.getY());
                // 判断是否是上下滑动
                if (!mDraggingInVertical && Math.abs(dy) > mTouchSlop && Math.abs(dy) > Math.abs(dx)) {
                    mDraggingInVertical = true;
                }
                //
                if (mDraggingInVertical) {
                    scrollBy(0, dy);
                    mLastX = (int) getX();
                    mLastY = (int) getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDraggingInVertical = false;
                mVelocityTracker.computeCurrentVelocity(1000, mScaledMaximumFlingVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                int velocity = Math.abs(velocityY);
                if (velocity > mScaledMinimumFlingVelocity) {
                    int duration = getYDuration(event, velocity);
                    if (velocityY < 0) {
                        smoothOpenTop(duration);
                    } else {
                        smoothCloseTop(duration);
                    }
                    ViewCompat.postInvalidateOnAnimation(this);
                }
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                //
                break;
            case MotionEvent.ACTION_CANCEL:
                mDraggingInVertical = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HistoryLayout,
                defStyleAttr, defStyleRes);
        mTopViewId = ta.getResourceId(R.styleable.HistoryLayout_top, mTopViewId);
        mContentViewId = ta.getResourceId(R.styleable.HistoryLayout_content, mContentViewId);
        ta.recycle();

        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(config);
        mScroller = new OverScroller(context);
        mScaledMinimumFlingVelocity = config.getScaledMinimumFlingVelocity();
        mScaledMaximumFlingVelocity = config.getScaledMaximumFlingVelocity();
    }

    private void smoothOpenTop(int duration) {
    }

    private void smoothCloseTop(int duration) {
    }


    private int getYDuration(MotionEvent event, int velocity) {
        int sy = getScrollY();
        int dy = (int) (event.getY() - sy);
        final int height = mTopView.getHeight();
        final int halfHeight = height / 2;
        final float ratio = Math.min(1.0f, 1.0f * Math.abs(dy) / height);
        final float distance = halfHeight + halfHeight * ratio;
        int duration;
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float pageDetal = (float) Math.abs(dy) / height;
            duration = (int) ((pageDetal + 1) * 100);
        }
        duration = Math.min(duration, mScrollerDuration);
        return duration;
    }

    // Setter & Getter
    public boolean isScrollable() {
        return mScrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.mScrollable = scrollable;
    }

    public boolean isDraggingInVertical() {
        return mDraggingInVertical;
    }

    public void setDraggingInVertical(boolean draggingInVertical) {
        this.mDraggingInVertical = draggingInVertical;
    }

    public int getScrollerDuration() {
        return mScrollerDuration;
    }

    public void setScrollerDuration(int scrollerDuration) {
        this.mScrollerDuration = scrollerDuration;
    }
}
