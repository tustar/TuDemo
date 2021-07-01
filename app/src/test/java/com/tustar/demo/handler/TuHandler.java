package com.tustar.demo.handler;

import androidx.annotation.NonNull;

public class TuHandler {

    final TuLooper mLooper;
    final TuMessageQueue mQueue;

    public TuHandler() {
        mLooper = TuLooper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
    }

    public void handleMessage(@NonNull TuMessage msg) {
    }

    public void dispatchMessage(TuMessage msg) {
        handleMessage(msg);
    }

    public final boolean sendMessage(@NonNull TuMessage msg) {
        return sendMessageDelayed(msg, 0);

    }

    public final boolean sendMessageDelayed(@NonNull TuMessage msg, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, System.currentTimeMillis() + delayMillis);
    }

    public boolean sendMessageAtTime(@NonNull TuMessage msg, long uptimeMillis) {
        TuMessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(
                    this + " sendMessageAtTime() called with no mQueue");
//                          Log.w("Looper", e.getMessage(), e);
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }

    private boolean enqueueMessage(@NonNull TuMessageQueue queue, @NonNull TuMessage msg,
                                   long uptimeMillis) {
        msg.target = this;
       return queue.enqueueMessage(msg, uptimeMillis);
    }
}
