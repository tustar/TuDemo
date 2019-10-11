package com.tustar.demo.ui.qyz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.common.IntentExtraKey;

public class QyzCustomWidgetActivity extends BaseActivity {

    public enum CustomType {
        MEASURE, ACTIONBAR, TEXTVIEW, CIRCLEPROGRESS, VOLUME, SCROLLVIEW, VIEWLAYOUT
    }

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_custom_widget);
        setTitle(R.string.qyz_drag_view);
        intent = new Intent(this, QyzCustomWidgetShowActivity.class);
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
