package com.tustar.demo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tustar on 16-8-2.
 */
public class ToastUtils {

    private static Toast mToast;

    public static void show(Context context, int resId, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, resId, duration);
        } else {
            mToast.setText(resId);
        }

        mToast.show();
    }

    public static void showShort(Context context, int resId) {
        show(context, resId, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, int resId) {
        show(context, resId, Toast.LENGTH_LONG);
    }

    public static void show(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
        }

        mToast.show();
    }

    public static void showShort(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_LONG);
    }
}
