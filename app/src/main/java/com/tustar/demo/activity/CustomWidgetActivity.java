package com.tustar.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tustar.demo.R;
import com.tustar.demo.common.IntentExtraKey;

public class CustomWidgetActivity extends BaseActivity {

    public enum CustomType {
        MEASURE, ACTIONBAR, TEXTVIEW
    }

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);
        intent = new Intent(this, CustomWidgetShowActivity.class);
    }

    public void customMeasure(View view) {
        startActivityByType(CustomType.MEASURE);
    }

    public void customActionBar(View view) {
        startActivityByType(CustomType.ACTIONBAR);
    }

    public void customTextView(View view) {
        startActivityByType(CustomType.TEXTVIEW);
    }

    private void startActivityByType(CustomType type) {
        intent.putExtra(IntentExtraKey.FLAG, type);
        startActivity(intent);
    }
}
