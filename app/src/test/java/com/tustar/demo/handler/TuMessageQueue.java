package com.tustar.demo.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TuMessageQueue {

    private final boolean mQuitAllowed;
    TuMessage mMessages;

    private boolean mBlocked;

    private BlockingQueue<TuMessage> blockingQueue = new ArrayBlockingQueue<>(50);

    public TuMessageQueue(boolean quitAllowed) {
        mQuitAllowed = quitAllowed;
    }

    public boolean enqueueMessage(TuMessage msg, long when) {
        System.out.println("enqueueMessage ::");
        try {
            blockingQueue.put(msg);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public TuMessage next() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
