package com.tustar.demo.ui.qyz.dragview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

public class QyzDragViewActivity extends BaseActivity {

    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_drag_view);
        setTitle(R.string.qyz_drag_view);
        intent = new Intent();
    }

    public void dragview(View view) {
        intent.setClass(this, QyzDragViewShowActivity.class);
        startActivity(intent);
    }

    public void dragviewgroup(View view) {
        intent.setClass(this, QyzDragViewGroupActivity.class);
        startActivity(intent);
    }
}
