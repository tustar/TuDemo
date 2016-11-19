package com.tustar.demo.module.scrollerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tustar.demo.R;

public class HScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscroll_view);
        setTitle(HScrollView.class.getSimpleName());
    }
}
