package com.tustar.demo.module.filemanager;

import android.os.Build;
import android.util.Log;

/**
 * Created by tustar on 15-10-14.
 */
public class Logger {

    public static final String APP_TAG = "TuDemo";

    private static final String VERSION_ENG = "eng";
    private static final String VERSION_USER = "user";
    private static final String VERSION_USER_DEBUG = "userdebug";

    public static boolean debug = false;

    static {
        switch (Build.TYPE) {
            case VERSION_USER:
                debug = false;
                break;
            case VERSION_ENG:
            case VERSION_USER_DEBUG:
                debug = true;
                break;
            default:
                Log.e(APP_TAG, "unknown build type, type = " + Build.TYPE);
                break;
        }
    }

    public static void v(String tag, String msg) {
        if (debug) {
            Log.v(APP_TAG, tag + ": " + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(APP_TAG, tag + ": " + msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(APP_TAG, tag + ": " + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (debug) {
            Log.w(APP_TAG, tag + ": " + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(APP_TAG, tag + ": " + msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (debug) {
            Log.e(APP_TAG, tag + ": " + msg, tr);
        }
    }
}
