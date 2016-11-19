package com.tustar.demo.module.scrollerdemo.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tustar.demo.R;
import com.tustar.demo.module.scrollerdemo.HOverScrollView;

public class OtherHOverScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover_scroll_view);
        setTitle(OtherHOverScrollView.class.getSimpleName());
    }
}
