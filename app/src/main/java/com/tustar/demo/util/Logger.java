package com.tustar.demo.util;

import android.os.Build;
import android.util.Log;

import com.tustar.demo.BuildConfig;

/**
 * Created by tustar on 15-10-14.
 */
public class Logger {


    /**
     * Default logger used for generic logging, i.eTAG. when a specific log tag isn't specified.
     */
    private final static TuLog DEFAULT_LOGGER = new TuLog("TuDemo");

    public static void v(String message, Object... args) {
        DEFAULT_LOGGER.v(message, args);
    }

    public static void d(String message, Object... args) {
        DEFAULT_LOGGER.d(message, args);
    }

    public static void i(String message, Object... args) {
        DEFAULT_LOGGER.i(message, args);
    }

    public static void w(String message, Object... args) {
        DEFAULT_LOGGER.w(message, args);
    }

    public static void e(String message, Object... args) {
        DEFAULT_LOGGER.e(message, args);
    }

    public static void e(String message, Throwable e) {
        DEFAULT_LOGGER.e(message, e);
    }

    public static void wtf(String message, Object... args) {
        DEFAULT_LOGGER.wtf(message, args);
    }

    public final static class TuLog {
        /**
         * Log everything for debug builds or if running on a dev device.
         */
        public final static boolean DEBUG = BuildConfig.DEBUG
                || "eng".equals(Build.TYPE)
                || "userdebug".equals(Build.TYPE);

        public final String logTag;

        public TuLog(String logTag) {
            this.logTag = logTag;
        }

        public boolean isVerboseLoggable() {
            return DEBUG || Log.isLoggable(logTag, Log.VERBOSE);
        }

        public boolean isDebugLoggable() {
            return DEBUG || Log.isLoggable(logTag, Log.DEBUG);
        }

        public boolean isInfoLoggable() {
            return DEBUG || Log.isLoggable(logTag, Log.INFO);
        }

        public boolean isWarnLoggable() {
            return DEBUG || Log.isLoggable(logTag, Log.WARN);
        }

        public boolean isErrorLoggable() {
            return DEBUG || Log.isLoggable(logTag, Log.ERROR);
        }

        public boolean isWtfLoggable() {
            return DEBUG || Log.isLoggable(logTag, Log.ASSERT);
        }

        public void v(String message, Object... args) {
            if (isVerboseLoggable()) {
                Log.v(logTag, args == null || args.length == 0
                        ? message : String.format(message, args));
            }
        }

        public void d(String message, Object... args) {
            if (isDebugLoggable()) {
                Log.d(logTag, args == null || args.length == 0 ? message
                        : String.format(message, args));
            }
        }

        public void i(String message, Object... args) {
            if (isInfoLoggable()) {
                Log.i(logTag, args == null || args.length == 0 ? message
                        : String.format(message, args));
            }
        }

        public void w(String message, Object... args) {
            if (isWarnLoggable()) {
                Log.w(logTag, args == null || args.length == 0 ? message
                        : String.format(message, args));
            }
        }

        public void e(String message, Object... args) {
            if (isErrorLoggable()) {
                Log.e(logTag, args == null || args.length == 0 ? message
                        : String.format(message, args));
            }
        }

        public void e(String message, Throwable e) {
            if (isErrorLoggable()) {
                Log.e(logTag, message, e);
            }
        }

        public void wtf(String message, Object... args) {
            if (isWtfLoggable()) {
                Log.wtf(logTag, args == null || args.length == 0 ? message
                        : String.format(message, args));
            }
        }
    }
}
