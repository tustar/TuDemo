package com.tustar.demo.handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TuLooper {

    private static final String TAG = "TuLooper";
    static final ThreadLocal<TuLooper> sThreadLocal = new ThreadLocal<>();

    private static TuLooper sMainLooper;

    final TuMessageQueue mQueue;
    final Thread mThread;

    public TuLooper(boolean quitAllowed) {
        mQueue = new TuMessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }

    public static void prepare() {
        prepare(true);
    }

    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new TuLooper(quitAllowed));
    }

    public static void prepareMainLooper() {
        prepare(false);
        synchronized (TuLooper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
    }

    public static @Nullable
    TuLooper myLooper() {
        return sThreadLocal.get();
    }

    public static @NonNull
    TuMessageQueue myQueue() {
        return myLooper().mQueue;
    }

    public boolean isCurrentThread() {
        return Thread.currentThread() == mThread;
    }

    public static TuLooper getMainLooper() {
        synchronized (TuLooper.class) {
            return sMainLooper;
        }
    }

    public static void loop() {
        final TuLooper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No TuLooper; TuLooper.prepare() wasn't called on this thread.");
        }
        final TuMessageQueue queue = me.mQueue;

        for (; ; ) {
            TuMessage msg = queue.next();
            if (msg != null) {
                msg.target.dispatchMessage(msg);
            }
        }
    }
}
