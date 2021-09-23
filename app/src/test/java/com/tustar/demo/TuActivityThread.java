package com.tustar.demo;

import androidx.annotation.NonNull;

import com.tustar.demo.handler.TuHandler;
import com.tustar.demo.handler.TuLooper;
import com.tustar.demo.handler.TuMessage;

import org.junit.Test;

public class TuActivityThread {

    @Test
    public void main() {
        TuLooper.prepareMainLooper();

        final TuHandler tuHandler = new TuHandler() {
            @Override
            public void handleMessage(@NonNull TuMessage msg) {
                System.out.println("handleMessage::");
                super.handleMessage(msg);
                System.out.printf(msg.obj.toString());
            }
        };

        new Thread(() -> {

            try {
                for (int i = 0; i < 5; i++) {
                    TuMessage msg = new TuMessage();
                    msg.what = i;
                    msg.obj = "Hello";
                    Thread.sleep(500);
                    tuHandler.sendMessage(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        TuLooper.loop();
    }
}
