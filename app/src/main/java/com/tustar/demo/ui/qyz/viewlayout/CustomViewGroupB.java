package com.tustar.demo.ui.qyz.viewlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.tustar.util.Logger;

/**
 * Created by tustar on 4/17/16.
 */
public class CustomViewGroupB extends LinearLayout {

    private static final String TAG = "CustomViewGroupB";

    public CustomViewGroupB(Context context) {
        super(context);
    }

    public CustomViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomViewGroupB(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Logger.i(TAG, "dispatchTouchEvent() called with: " + "ev = [" + ev + "]");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Logger.i(TAG, "onInterceptTouchEvent() called with: " + "ev = [" + ev + "]");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Logger.i(TAG, "onTouchEvent() called with: " + "event = [" + event + "]");
        return super.onTouchEvent(event);
    }
}
