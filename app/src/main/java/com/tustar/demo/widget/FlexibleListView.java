package com.tustar.demo.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Created by tustar on 4/18/16.
 */
public class FlexibleListView extends ListView {

    private static int mMaxOverDistance = 50;
    private Context mContext;

    public FlexibleListView(Context context) {
        super(context);

        this.mContext = context;
        init();
    }

    public FlexibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;
        init();
    }

    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.mContext = context;
        init();
    }


    private void init() {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        mMaxOverDistance = (int) (density * mMaxOverDistance);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY,
                                   int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY,
                scrollX, scrollY,
                scrollRangeX, scrollRangeY,
                maxOverScrollX, mMaxOverDistance,
                isTouchEvent);
    }
}
