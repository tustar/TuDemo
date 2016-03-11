package com.tustar.demo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.WindowManager;

import com.tustar.demo.R;
import com.tustar.demo.model.SignalInfo;
import com.tustar.demo.util.Logger;
import com.tustar.demo.widget.PhoneStateFloatView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tustar on 16-2-16.
 */
public class PhoneStateService extends Service {

    private static final String TAG = PhoneStateService.class.getSimpleName();

    // Action
    public static final String ACTION_SHOW_PHONE_STATE = "com.zui.rf.action.show.phone.state";
    public static final String ACTION_REMOVE_PHONE_STATE = "com.zui.rf.action.remove.phone.state";

    // PhoneStateListener
    private TelephonyManager mTelephonyManager;
    private ZuiPhoneStateListener mZuiPhoneStateListener;
    private List<SignalInfo> mInfos = new ArrayList<>();

    // Float view
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private PhoneStateFloatView mPhoneStateFloatView;

    // Message & Handler
    private static final int MSG_UPDATE_SIGNAL_STRENGTH = 0x1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_SIGNAL_STRENGTH:
                    Logger.i(TAG, "handleMessage :: MSG_UPDATE_SIGNAL_STRENGTH");
                    mInfos.clear();
                    SignalStrength signalStrength = (SignalStrength) msg.obj;
//                    Toast.makeText(getApplicationContext(), signalStrength.toString(), Toast.LENGTH_SHORT).show();
                    SignalInfo info1 = new SignalInfo();
                    info1.setSim(getString(R.string.rf_signal_sim, "1"));
                        info1.setNetwork(getString(R.string.rf_signal_network, "gsm"));
                        info1.setProp1(getString(R.string.rf_gsm_bit_error_rate,
                                signalStrength.getGsmBitErrorRate()));
                        info1.setProp2(getString(R.string.rf_gsm_signal_strength,
                                signalStrength.getGsmSignalStrength()));
                    mInfos.add(info1);

                    SignalInfo info2 = new SignalInfo();
                    info2.setSim(getString(R.string.rf_signal_sim, "2"));
                    info2.setNetwork(getString(R.string.rf_signal_network, "cdma"));
                    info2.setProp1(getString(R.string.rf_cdma_rssi, signalStrength.getCdmaDbm()));
                    info2.setProp2(getString(R.string.rf_cdma_ecio, signalStrength.getCdmaEcio()));
                    mInfos.add(info2);

                    mPhoneStateFloatView.updateSignalInofs(mInfos);

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Logger.i(TAG, "onCreate ::");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(TAG, "onStartCommand :: intent = " + intent + ", flags = " + flags
                + ", startId = " + startId);
        if (null == intent) {
            Logger.w(TAG, "onStartCommand :: intent is null");
            return super.onStartCommand(intent, flags, startId);
        }

        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            Logger.w(TAG, "onStartCommand :: action is empty");
            return super.onStartCommand(intent, flags, startId);
        }

        switch (action) {
            case ACTION_SHOW_PHONE_STATE:
                Logger.d(TAG, "onStartCommand :: ACTION_SHOW_PHONE_STATE");
                initPhoneStateView(getApplicationContext());
                break;
            case ACTION_REMOVE_PHONE_STATE:
                Logger.d(TAG, "onStartCommand :: ACTION_REMOVE_PHONE_STATE");
                unregisterPhoneStateListener();
                removeFloatView();
                stopSelf();
                break;
            default:
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.i(TAG, "onDestroy ::");
        unregisterPhoneStateListener();
        removeFloatView();
        super.onDestroy();
    }

    private void initPhoneStateView(Context context) {
        Logger.i(TAG, "initPhoneStateView ::");
        mPhoneStateFloatView = new PhoneStateFloatView(getApplicationContext(), mInfos);
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mZuiPhoneStateListener = new ZuiPhoneStateListener();
        mTelephonyManager.listen(mZuiPhoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        addFloatView(context, mPhoneStateFloatView);
    }

    private void addFloatView(Context context, PhoneStateFloatView view) {
        Logger.i(TAG, "addFloatView ::");
        mWindowManager = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.format= PixelFormat.TRANSLUCENT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        mLayoutParams.x = 0;
        mLayoutParams.y = 0;

        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWindowManager.addView(view, mLayoutParams);
    }

    private void unregisterPhoneStateListener() {
        if (null != mTelephonyManager) {
            mTelephonyManager.listen(mZuiPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }

    private void removeFloatView() {
        Logger.i(TAG, "removeFloatView ::");
        if (null != mPhoneStateFloatView) {
            mWindowManager.removeViewImmediate(mPhoneStateFloatView);
            mPhoneStateFloatView = null;
        }
    }

    class ZuiPhoneStateListener extends PhoneStateListener {

        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            super.onServiceStateChanged(serviceState);
        }

        @Override
        public void onMessageWaitingIndicatorChanged(boolean mwi) {
            super.onMessageWaitingIndicatorChanged(mwi);
        }

        @Override
        public void onCallForwardingIndicatorChanged(boolean cfi) {
            super.onCallForwardingIndicatorChanged(cfi);
        }

        @Override
        public void onCellLocationChanged(CellLocation location) {
            super.onCellLocationChanged(location);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
        }

        @Override
        public void onDataConnectionStateChanged(int state) {
            super.onDataConnectionStateChanged(state);
        }

        @Override
        public void onDataConnectionStateChanged(int state, int networkType) {
            super.onDataConnectionStateChanged(state, networkType);
        }

        @Override
        public void onDataActivity(int direction) {
            super.onDataActivity(direction);
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            Logger.d(TAG, "onSignalStrengthsChanged :: signalStrength = " + signalStrength);
            super.onSignalStrengthsChanged(signalStrength);
            Message msg = new Message();
            msg.what = MSG_UPDATE_SIGNAL_STRENGTH;
            msg.obj = signalStrength;
            mHandler.sendMessage(msg);
        }

        @Override
        public void onCellInfoChanged(List<CellInfo> cellInfo) {
            super.onCellInfoChanged(cellInfo);
        }
    }
}
