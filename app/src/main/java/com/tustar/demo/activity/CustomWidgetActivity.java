package com.tustar.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tustar.demo.R;
import com.tustar.demo.common.IntentExtraKey;

public class CustomWidgetActivity extends BaseActivity {

    public enum CustomType {
        MEASURE, ACTIONBAR, TEXTVIEW, CIRCLEPROGRESS, VOLUME, SCROLLVIEW, VIEWLAYOUT
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

    public void customCircleProgress(View view) {
        startActivityByType(CustomType.CIRCLEPROGRESS);
    }

    public void customVolume(View view) {
        startActivityByType(CustomType.VOLUME);
    }

    public void customScrollView(View view) {
        startActivityByType(CustomType.SCROLLVIEW);
    }

    public void customViewLayout(View view) {
        startActivityByType(CustomType.VIEWLAYOUT);
    }

    private void startActivityByType(CustomType type) {
        intent.putExtra(IntentExtraKey.FLAG, type);
        startActivity(intent);
    }
}
