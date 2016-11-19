package com.tustar.demo.module.servicedemo;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.tustar.demo.util.Logger;

public class DemoIntentService extends IntentService {

    private static final String TAG = "DemoIntentService";

    private static final String ACTION_DEMO = "com.tustar.demo.service.action.DEMO";

    private static final String EXTRA_PARAM1 = "com.tustar.demo.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.tustar.demo.service.extra.PARAM2";

    public DemoIntentService() {
        super("DemoIntentService");
    }

    public static void startActionDemo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DemoIntentService.class);
        intent.setAction(ACTION_DEMO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        Logger.i(TAG, "onCreate :: ");
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (null == intent) {
            Logger.w(TAG, "onHandleIntent :: intent is null!!!");
            return;
        }

        final String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Logger.w(TAG, "onHandleIntent :: action is empty!!!");
            return;
        }

        switch (action) {
            case ACTION_DEMO:
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionDemo(param1, param2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        Logger.i(TAG, "onDestroy :: ");
        super.onDestroy();
    }

    private void handleActionDemo(String param1, String param2) {
        Logger.i(TAG, "handleActionDemo :: " + "param1 = [" + param1
                + "], param2 = [" + param2 + "]");
    }
}
