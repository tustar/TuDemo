package com.tustar.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tustar.demo.R;
import com.tustar.demo.common.IntentExtraKey;

public class CustomWidgetActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);
        intent = new Intent(this, CustomWidgetShowActivity.class);
    }

    public void teaching(View view) {
        intent.putExtra(IntentExtraKey.FLAG, 0);
        startActivity(intent);
    }
}
