package com.tustar.demo.widget.dragview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
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
    private int mTopViewHeight;
    private int mContentViewHeight;
    private int mContentViewTop = 0;

    private boolean mScrollable = true;
    private ViewDragHelper mViewDragHelper;
    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Logger.i(TAG, "clampViewPositionVertical :: top = " + top
                    + ", dy = " + dy);
            if (!mScrollable) {
                return 0;
            }
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Logger.i(TAG, "onViewReleased :: xvel = " +
                    xvel + ", yvel = " + yvel);
            super.onViewReleased(releasedChild, xvel, yvel);
            int top = mContentView.getTop();
            Logger.d(TAG, "onViewReleased :: top = " + top);
//            if (top < mContentViewHeight / 2) {
//                mViewDragHelper.smoothSlideViewTo(mTopView, 0, mContentViewTop - mTopViewHeight);
//                mViewDragHelper.smoothSlideViewTo(mContentView, 0, mContentViewTop);
//                ViewCompat.postInvalidateOnAnimation(HistoryLayout.this);
//            } else {
//                mViewDragHelper.smoothSlideViewTo(mTopView, 0, 0);
//                mViewDragHelper.smoothSlideViewTo(mContentView, 0, mHeight);
//                ViewCompat.postInvalidateOnAnimation(HistoryLayout.this);
//            }
        }
    };

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

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HistoryLayout,
                defStyleAttr, defStyleRes);
        mTopViewId = ta.getResourceId(R.styleable.HistoryLayout_top, mTopViewId);
        mContentViewId = ta.getResourceId(R.styleable.HistoryLayout_content, mContentViewId);
        ta.recycle();

        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d(TAG, "onMeasure :: ");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Logger.d(TAG, "onMeasure :: mWidth = " + mWidth + ", mHeight = " + mHeight);
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
        Logger.i(TAG, "onLayout :: ");
        int parentHeight = getMeasuredHeightAndState();
        if (mContentView != null) {
            int contentViewWidth = mContentView.getMeasuredWidthAndState();
            int contentViewHeight = mContentView.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
            int l = mContentView.getPaddingLeft();
            int t = parentHeight - contentViewHeight + lp.topMargin;
            mContentViewTop = t;
            int r = l + contentViewWidth;
            int b = t + contentViewHeight;
            mContentView.layout(l, t, r, b);
        }
        if (mTopView != null) {
            int topViewWidth = mTopView.getMeasuredWidthAndState();
            int topViewHeight = mTopView.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) mTopView.getLayoutParams();
            int l = mTopView.getPaddingLeft();
            int t = mContentViewTop + lp.topMargin - topViewHeight;
            int r = l + topViewWidth;
            int b = t + topViewHeight;
            mTopView.layout(l, t, r, b);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTopView.getHeight();
        mContentViewHeight = mContentView.getHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    // Setter & Getter
    public boolean isScrollable() {
        return mScrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.mScrollable = scrollable;
    }
}
