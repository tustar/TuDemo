package com.tustar.demo.module.signal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.common.Preferences;
import com.tustar.common.util.Logger;
import com.tustar.common.util.PreferencesUtils;


public class FloatWindowActivity extends BaseActivity {

    private static final String TAG = FloatWindowActivity.class.getSimpleName();
    private Switch mRfSwitch;
    private Context mContext;
    private boolean mIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate ::");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);
        mContext = this;

        mRfSwitch = (Switch)findViewById(R.id.rf_switch);
        mRfSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsChecked = isChecked;
                PreferencesUtils.putBoolean(mContext, Preferences.PREF_KEY_SIGNAL_CHECKED,
                        isChecked);
                initService(isChecked);
            }
        });
    }

    @Override
    protected void onResume() {
        Logger.i(TAG, "onResume ::");
        super.onResume();

        mRfSwitch.setChecked(PreferencesUtils.getBoolean(mContext,
                Preferences.PREF_KEY_SIGNAL_CHECKED));
    }

    @Override
    protected void onPause() {
        Logger.i(TAG, "onPause ::");
        super.onPause();

        PreferencesUtils.putBoolean(mContext, Preferences.PREF_KEY_SIGNAL_CHECKED, mIsChecked);
    }

    private void initService(boolean isChecked) {
        Intent intent = new Intent(isChecked ? PhoneStateService.ACTION_SHOW_PHONE_STATE
                : PhoneStateService.ACTION_REMOVE_PHONE_STATE);
        intent.setClass(mContext, PhoneStateService.class);
        startService(intent);
    }
}
