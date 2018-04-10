package com.tustar.common.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by tustar on 4/16/16.
 */
public class DisplayUtils {

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }
}
