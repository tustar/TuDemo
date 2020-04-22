package com.tustar.demo.ui.qyz;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;

import com.tustar.util.Logger;
import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;

public class QyzRotationActivity extends BaseActivity {
    /**
     * Constant for an invalid resource id.
     */
    public static final int SYSTEM_ROTATION_UNLOCK = 1;
    private static final String TAG = QyzRotationActivity.class.getName();
    private static final int THRESHOLD = 15;
    private static final int MSG_SYSTEM_ROTATION_CHANGED = 0x1;
    private Context mContext;
    //
    private int mSwitchOrientation = Configuration.ORIENTATION_PORTRAIT;
    private boolean isChecked;
    private boolean mBtnRotationLock = false;
    // OrientationEventListener
    private OrientationEventListener mOrientationEventListener;
    private UnleakHandler mHandler = new UnleakHandler(this);
    private RotationObserver mRotationObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.v(TAG, "onCreate :: savedInstanceState = " + savedInstanceState);
        super.onCreate(savedInstanceState);
        setTitle(R.string.qyz_rotation_title);
        mContext = this;

        // Register rotation observer
        registerRotationObserver();

        init(savedInstanceState);
    }


    @Override
    protected void onResume() {
        Logger.i(TAG, "onResume ::");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Logger.i(TAG, "onPause ::");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Logger.i(TAG, "onStop ::");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Logger.i(TAG, "onDestroy ::");
        super.onDestroy();

        // Unregister rotation observer
        unregisterRotationObserver();
    }

    private void registerRotationObserver() {
        if (null == mRotationObserver) {
            mRotationObserver = new RotationObserver(mHandler);
        }
        getContentResolver().registerContentObserver(Settings.System.getUriFor
                        (Settings.System.ACCELEROMETER_ROTATION),
                true, mRotationObserver);
    }

    private void unregisterRotationObserver() {
        if (null != mRotationObserver) {
            getContentResolver().unregisterContentObserver(mRotationObserver);
        }
    }

    private void init(Bundle savedInstanceState) {
        if (!isStandardCalculator()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_qyz_rotation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Init OrientationEventListener
        initOrientationEventListener();
    }

    public void rotationScreen(View view) {
        Logger.i(TAG, "rotationScreen :: view = " + view);
        isChecked = !isChecked;
        if (isChecked) {
            // Analytic DATAS
            mSwitchOrientation = Configuration.ORIENTATION_LANDSCAPE;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            // Analytic DATAS
            mSwitchOrientation = Configuration.ORIENTATION_PORTRAIT;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // Only system rotation lock is unlock, the button rotation can use.
        if (isSystemRotationUnlock()) {
            mBtnRotationLock = true;
        }
    }

    private void initOrientationEventListener() {
        // OrientationEventListener
        mOrientationEventListener = new OrientationEventListener(this) {

            @Override
            public void onOrientationChanged(int orientation) {
                int screenOrientation = getResources().getConfiguration().orientation;
                if ((screenOrientation == Configuration.ORIENTATION_PORTRAIT
                        && isPortrait(orientation)
                        && mSwitchOrientation == Configuration.ORIENTATION_PORTRAIT)
                        || (screenOrientation == Configuration.ORIENTATION_LANDSCAPE
                        && isLandscape(orientation)
                        && mSwitchOrientation == Configuration.ORIENTATION_LANDSCAPE)) {
                    mBtnRotationLock = false;
                    if (isSystemRotationUnlock()
                            && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_USER) {
                        Logger.d(TAG, "onOrientationChanged :: Reopen system rotation!!!");
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                    }
                }
            }
        };

        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        } else {
            mOrientationEventListener.disable();
        }
    }

    private int getSystemRotationLock() {
        int systemRotationLock = 0;
        try {
            systemRotationLock = Settings.System.getInt(mContext.getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemRotationLock;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Logger.v(TAG, "onConfigurationChanged :: newConfig = " + newConfig);
        super.onConfigurationChanged(newConfig);
        Logger.v(TAG, "onConfigurationChanged :: newConfig.orientation = " + newConfig.orientation);
        Logger.v(TAG, "onConfigurationChanged :: mSwitchOrientation = " + mSwitchOrientation);
        Logger.v(TAG, "onConfigurationChanged :: mBtnRotationLock = " + mBtnRotationLock);
        // Rotation lock state
        if (mBtnRotationLock) {
            // Unlock orientation
            if (mSwitchOrientation == newConfig.orientation) {
                Logger.d(TAG, "onConfigurationChanged :: Unlock rotation will orientation " +
                        "is really same!!!!");
                mBtnRotationLock = false;
                mSwitchOrientation = newConfig.orientation;
            } else {
                Logger.d(TAG, "onConfigurationChanged :: Can't unlock rotation, " +
                        "reset to old orientation!!!");
            }

            resetOrientation();
        }
        // Rotation unlock state
        else {
            mSwitchOrientation = newConfig.orientation;

        }

        init(null);
    }

    private boolean isSystemRotationUnlock() {
        return getSystemRotationLock() == SYSTEM_ROTATION_UNLOCK;
    }

    private void resetOrientation() {
        if (mSwitchOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (mSwitchOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    private boolean isStandardCalculator() {
        return mSwitchOrientation == Configuration.ORIENTATION_PORTRAIT;
    }


    private boolean isLandscape(int orientation) {
        return (orientation >= (90 - THRESHOLD) && orientation <= (90 + THRESHOLD))
                || (orientation >= (270 - THRESHOLD) && orientation <= (270 + THRESHOLD));
    }

    private boolean isPortrait(int orientation) {
        return (orientation >= (180 - THRESHOLD) && orientation <= (180 + THRESHOLD))
                || (orientation >= (360 - THRESHOLD) && orientation <= (0 + THRESHOLD));
    }


    class UnleakHandler extends Handler {

        private WeakReference<AppCompatActivity> activity;

        public UnleakHandler(AppCompatActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (null == activity.get()) {
                return;
            }

            switch (msg.what) {
                case MSG_SYSTEM_ROTATION_CHANGED:
                    Logger.i(TAG, "handleMessage :: MSG_SYSTEM_ROTATION_CHANGED");
                    // Reset button rotation lock to unlock.
                    mBtnRotationLock = false;
                    if (isSystemRotationUnlock()) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    class RotationObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public RotationObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            Logger.i(TAG, "onChange :: selfChange = " + selfChange);
            super.onChange(selfChange);
            mHandler.sendEmptyMessage(MSG_SYSTEM_ROTATION_CHANGED);
        }
    }
}
