package com.tustar.demo.util;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tustar on 15-10-14.
 */
public class Logger {

    private static final String APP_TAG = "TuDemo";
    private static final String LOG_FILE_NAME = APP_TAG + ".txt";
    private static final SimpleDateFormat LOG_DATE_FORMATTER = new
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

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

    public static void v(String tag, String msg, boolean isOut2SDCard) {
        v(tag, msg);
        if (isOut2SDCard) {
            writeLog2Sdcard(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (debug) {
            Log.d(APP_TAG, tag + ": " + msg);
        }
    }

    public static void d(String tag, String msg, boolean isOut2SDCard) {
        d(tag, msg);
        if (isOut2SDCard) {
            writeLog2Sdcard(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (debug) {
            Log.i(APP_TAG, tag + ": " + msg);
        }
    }

    public static void i(String tag, String msg, boolean isOut2SDCard) {
        i(tag, msg);
        if (isOut2SDCard) {
            writeLog2Sdcard(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (debug) {
            Log.w(APP_TAG, tag + ": " + msg);
        }
    }

    public static void w(String tag, String msg, boolean isOut2SDCard) {
        w(tag, msg);
        if (isOut2SDCard) {
            writeLog2Sdcard(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (debug) {
            Log.e(APP_TAG, tag + ": " + msg);
        }
    }

    public static void e(String tag, String msg, boolean isOut2SDCard) {
        e(tag, msg);
        if (isOut2SDCard) {
            writeLog2Sdcard(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (debug) {
            Log.e(APP_TAG, tag + ": " + msg, tr);
        }
    }

    public static void e(String tag, String msg, boolean isOut2SDCard, Throwable t) {
        e(tag, msg, t);
        if (isOut2SDCard) {
            writeLog2Sdcard(tag, msg);
        }
    }

    public static synchronized void writeLog2Sdcard(final String tag, final String... messages) {
        HandlerThread handlerThread = new HandlerThread(Logger.class.getSimpleName());
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(() -> write2Sdcard(tag, messages));
    }

    public static void write2Sdcard(String tag, String... messages) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        PrintWriter writer = null;
        try {

            File file = new File(Environment.getExternalStorageDirectory(), LOG_FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new PrintWriter(new FileWriter(file, true), true);
            String time = LOG_DATE_FORMATTER.format(new Date());
            String prefix = time + " " + tag + " : ";
            if (null == messages) {
                writer.println(prefix + "");
            } else if (1 == messages.length) {
                writer.println(prefix + messages[0]);
            } else {
                writer.println(prefix + "╔══════════════════════════════════════════════════════════════════════");
                for (String message : messages) {
                    writer.println(prefix + "║" + message);
                }
                writer.println(prefix + "╚══════════════════════════════════════════════════════════════════════");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}
