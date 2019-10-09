package com.tustar.demo.ui.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import com.tustar.common.util.Logger;
import com.tustar.demo.ui.main.MainActivity;
import com.tustar.demo.R;
import com.tustar.demo.service.IDemoAidlService;

import androidx.core.app.NotificationCompat;

public class DemoService extends Service {

    private static final String TAG = "DemoService";
    //    private DemoBinder mBinder = new DemoBinder();
    private IDemoAidlService.Stub mBinder = new IDemoAidlService.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            Logger.i(TAG, "plus :: " + "a = [" + a + "], b = [" + b + "]");
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            Logger.i(TAG, "toUpperCase :: " + "str = [" + str + "]");
            if (null != str) {
                return str.toUpperCase();
            }

            return null;
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        Logger.i(TAG, "onBind :: ");
        return mBinder;
    }

    @Override
    public void onCreate() {
        Logger.i(TAG, "onCreate :: ");
        Logger.d(TAG, "onCreate :: DemoService thread id = " + Thread.currentThread().getId());
        Logger.d(TAG, "onCreate :: DemoService process id = " + Process.myPid());
        super.onCreate();

        //
        startForegroundNotification();

        // 测试是否在主线程中运行
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(TAG, "onStartCommand :: " + "intent = [" + intent + "], flags = [" + flags
                + "], startId = [" + startId + "]");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开始执行后台任务
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.i(TAG, "onDestroy :: ");
        super.onDestroy();
    }

    //
    private void startForegroundNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = builder
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("新的Demo")
                .setContentText("新的知识点")
                .setContentIntent(contentPendingIntent)
                .setWhen(System.currentTimeMillis())
                .build();
        startForeground(1, notification);
    }
}


