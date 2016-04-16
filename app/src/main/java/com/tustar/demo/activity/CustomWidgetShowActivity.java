package com.tustar.demo.activity;

import android.os.Bundle;

import com.tustar.demo.R;
import com.tustar.demo.common.IntentExtraKey;

public class CustomWidgetShowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = getIntent().getIntExtra(IntentExtraKey.FLAG, -1);
        switch (flag) {
            case 0:
                setContentView(R.layout.activity_custom_widget_teaching);
                break;
        }
    }
}
