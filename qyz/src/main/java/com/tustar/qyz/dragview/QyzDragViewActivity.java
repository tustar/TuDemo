package com.tustar.qyz.dragview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tustar.qyz.R;
import androidx.appcompat.app.AppCompatActivity;

public class QyzDragViewActivity extends AppCompatActivity {

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
