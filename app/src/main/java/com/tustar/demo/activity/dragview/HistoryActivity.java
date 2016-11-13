package com.tustar.demo.activity.dragview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tustar.demo.R;
import com.tustar.demo.util.ToastUtils;
import com.tustar.demo.widget.dragview.HistoryLayout;

public class HistoryActivity extends AppCompatActivity implements HistoryLayout.OnTopStateListener{

    private HistoryLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mLayout = (HistoryLayout)findViewById(R.id.history_layout);
        mLayout.setOnTopStateListener(this);
    }

    @Override
    public void onTopViewOpen() {
        ToastUtils.show(this, "Top View open", Toast.LENGTH_SHORT);
    }

    @Override
    public void onTopViewClose() {
        ToastUtils.show(this, "Top View close", Toast.LENGTH_SHORT);
    }
}
