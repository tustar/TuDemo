package com.tustar.demo.activity.dragview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tustar.demo.R;
import com.tustar.demo.activity.BaseActivity;

public class DragViewActivity extends BaseActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);
        intent = new Intent();
    }

    public void dragview(View view) {
        intent.setClass(this, DragViewShowActivity.class);
        startActivity(intent);
    }

    public void dragviewgroup(View view) {
        intent.setClass(this, DragViewGroupActivity.class);
        startActivity(intent);
    }
}
