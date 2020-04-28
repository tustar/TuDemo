package com.tustar.demo.ui.component;

import android.os.Binder;

import com.tustar.util.Logger;

/**
 * Created by tustar on 6/18/16.
 */
public class DemoBinder extends Binder {

    private static final String TAG = "DemoBinder";

    public void startDownload() {
        Logger.i(TAG, "startDownload :: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开始执行后台任务
            }
        }).start();
    }
}
