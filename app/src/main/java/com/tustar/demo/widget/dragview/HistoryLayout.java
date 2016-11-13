package com.tustar.demo.widget.dragview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.tustar.demo.R;
import com.tustar.demo.util.Logger;

/**
 * Created by tustar on 16-11-11.
 */

public class HistoryLayout extends FrameLayout {

    private static final String TAG = "HistoryLayout";

    private int mWidth;
    private int mHeight;

    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;

    private int mTopViewId = 0;
    private int mContentViewId = 0;
    private View mTopView;
    private View mContentView;
    private int mContentViewTop = 0;

    private OverScroller mScroller;
    private int mTouchSlop;
    private int mMaxVelocity;
    private int mMinVelocity;

    // flag
    private boolean mScrollable = true;
    private boolean mDragging;

    // Callback
    private OnTopStateListener mTopStateListener;

    //
    private int mMaxOffsetY;

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

        mScroller = new OverScroller(context);
        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledTouchSlop();
        mMaxVelocity = config.getScaledMaximumFlingVelocity();
        mMinVelocity = config.getScaledMinimumFlingVelocity();
        Logger.d(TAG, "init :: mTouchSlop = " + mTouchSlop);
        Logger.d(TAG, "init :: mMaxVelocity = " + mMaxVelocity);
        Logger.d(TAG, "init :: mMinVelocity = " + mMinVelocity);
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
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG, "onTouchEvent :: ACTION_DOWN");
                mDownX = mLastX = (int) event.getX();
                mDownY = mLastY = (int) event.getY();
                Logger.d(TAG, "onTouchEvent :: ACTION_DOWN :: mDownY = " + mDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, "onTouchEvent :: ACTION_MOVE");
                if (!mScrollable) {
                    break;
                }
                int x = (int) event.getX();
                int y = (int) event.getY();
                int dx = mLastX - x;
                int dy = mLastY - y;
                Logger.d(TAG, "onTouchEvent :: ACTION_MOVE :: dy = " + dy);
                int scrollY = getScrollY();
                Logger.d(TAG, "onTouchEvent :: ACTION_MOVE :: scrollY = " + scrollY);
                mMaxOffsetY = mTopView.getMeasuredHeightAndState() - mContentView.getTop();
                Logger.d(TAG, "onTouchEvent :: ACTION_MOVE :: mMaxOffsetY = " + mMaxOffsetY);

                // 当topView到达顶部，不能向上滑动
                if (dy < 0 && -scrollY >= mMaxOffsetY) {
                    Logger.w(TAG, "onTouchEvent :: ACTION_MOVE :: Top view is on the top.");
                    mDragging = false;
                    break;
                }
                // 当contentView到达底部时，不能向下滑动
                else if (dy > 0 && scrollY >= 0) {
                    Logger.w(TAG, "onTouchEvent: ACTION_MOVE :: Content view is on the bottom.");
                    mDragging = false;
                    break;
                }
                if (!mDragging && Math.abs(dy) > mTouchSlop && Math.abs(dy) > Math.abs(dx)) {
                    mDragging = true;
                }
                if (mDragging) {
                    if (autoScroll(dy)) {
                        break;
                    }

                    scrollBy(0, dy);
                    mLastX = x;
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                Logger.d(TAG, "onTouchEvent :: ACTION_UP");
                int disY = (int) (mDownY - event.getY());
                scrollY = getScrollY();
                Logger.d(TAG, "onTouchEvent :: ACTION_UP :: disY = " + disY);
                Logger.d(TAG, "onTouchEvent :: ACTION_UP :: scrollY = " + scrollY);
                if (autoScroll(disY)) {
                    break;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                Logger.d(TAG, "onTouchEvent :: ACTION_CANCEL");
                break;
        }
        return true;
    }

    private boolean autoScroll(int dy) {
        // 向上滑动超过mMaxOffsetY的1/3时，直接滑上，mContentView
        int scrollY = getScrollY();
        Logger.d(TAG, "autoScroll :: scrollY = " + scrollY);
        if (dy > 0 && scrollY <= mMaxOffsetY / 1) {
            Logger.d(TAG, "autoScroll :: Full up.");
            mTopView.setEnabled(false);
            mContentView.setEnabled(true);
            mScroller.startScroll(0, scrollY, 0, -scrollY);
            mDragging = false;
            invalidate();
            if (mTopStateListener != null) {
                mTopStateListener.onTopViewClose();
            }
            return true;
        }
        // 向下滑动超过mMaxOffsetY的2/3时，直接滑下，显示整个mTopView
        else if (dy < 0 && -scrollY <= mMaxOffsetY * 2 / 3) {
            Logger.d(TAG, "autoScroll :: Full down.");
            mTopView.setEnabled(true);
            mContentView.setEnabled(false);
            mScroller.startScroll(0, scrollY, 0, -mMaxOffsetY-scrollY);
            mDragging = false;
            invalidate();
            if (mTopStateListener != null) {
                mTopStateListener.onTopViewOpen();
            }
            return true;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }

    // Setter & Getter
    public boolean isScrollable() {
        return mScrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.mScrollable = scrollable;
    }

    public void setOnTopStateListener(OnTopStateListener mTopStateListener) {
        this.mTopStateListener = mTopStateListener;
    }

    public interface OnTopStateListener {
        void onTopViewOpen();
        void onTopViewClose();
    }
}
