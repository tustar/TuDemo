package com.tustar.demo.ui.component;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.util.Logger;

public class DemoServiceActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "DemoServiceActivity";

    Button mStartService;
    Button mStopService;
    Button mBindService;
    Button mUnbindService;
    Button mStartIntentService;

    private DemoServiceConnection mConnection = new DemoServiceConnection();
    //    private DemoBinder mBinder;
    private IDemoAidlService mIDemoAidlService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate :: ");
        Logger.d(TAG, "onCreate :: ServiceActivity thread id = " + Thread.currentThread().getId());
        Logger.d(TAG, "onCreate :: ServiceActivity process id = " + Process.myPid());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_service);
        setTitle(R.string.demo_service_title);

        mStartService = findViewById(R.id.start_service);
        mStartService.setOnClickListener(this);
        mStopService = findViewById(R.id.stop_service);
        mStopService.setOnClickListener(this);
        mBindService = findViewById(R.id.bind_service);
        mBindService.setOnClickListener(this);
        mUnbindService = findViewById(R.id.unbind_service);
        mUnbindService.setOnClickListener(this);
        mStartIntentService = findViewById(R.id.start_intent_service);
        mStartIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_service:
                Logger.i(TAG, "onClick :: start_service");
                startService(new Intent(this, DemoService.class));
                break;
            case R.id.stop_service:
                Logger.i(TAG, "onClick :: stop_service");
                stopService(new Intent(this, DemoService.class));
                break;
            case R.id.bind_service:
                Logger.i(TAG, "onClick :: bind_service");
                bindService(new Intent(this, DemoService.class), mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Logger.i(TAG, "onClick :: unbind_service");
                unbindService(mConnection);
                break;
            case R.id.start_intent_service:
                Logger.i(TAG, "onClick :: start_intent_service");
                DemoIntentService.startActionDemo(this, "Param1", "Param2");
                break;
            default:
                break;
        }
    }

    class DemoServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Logger.i(TAG, "onServiceConnected :: " + "name = [" + name
                    + "], service = [" + service + "]");
//            mBinder = (DemoBinder) service;
//            mBinder.startDownload();

            mIDemoAidlService = IDemoAidlService.Stub.asInterface(service);
            try {
                int result = mIDemoAidlService.plus(1, 2);
                String upperStr = mIDemoAidlService.toUpperCase("hello");
                Logger.d(TAG, "onServiceConnected :: result = " + result);
                Logger.d(TAG, "onServiceConnected :: upperStr = " + upperStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Logger.i(TAG, "onServiceDisconnected :: " + "name = [" + name + "]");
        }
    }
}
