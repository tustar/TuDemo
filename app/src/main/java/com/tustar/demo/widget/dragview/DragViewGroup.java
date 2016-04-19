package com.tustar.demo.widget.dragview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by tustar on 4/19/16.
 */
public class DragViewGroup extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mMenuView;
    private View mMainView;
    private int mWidth;

    public DragViewGroup(Context context) {
        super(context);
        init();
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DragViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        // 何时开始检测触摸事件
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            // 如果当前触摸的child是mMainView时开始检测
            return child == mMainView;
        }

        // 处理垂直滑动
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        // 处理水平滑动
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        // 拖动结束后调用

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            // 手指抬起后缓慢移动到指定位置
            if (mMainView.getLeft() < 500) {
                // 关闭菜单
                // 相当于Scroller的startScroll方法
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            } else {
                // 打开菜单
                mViewDragHelper.smoothSlideViewTo(mMainView, 300, 0);
                ViewCompat.postInvalidateOnAnimation(DragViewGroup.this);
            }
        }
    };

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mMenuView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件传递给ViewDragHelper,此操作必不可少
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
