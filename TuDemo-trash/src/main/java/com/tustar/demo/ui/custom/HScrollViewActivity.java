package com.tustar.demo.ui.custom;

import android.os.Bundle;

import com.tustar.demo.R;

import androidx.appcompat.app.AppCompatActivity;

public class HScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscroll_view);
        setTitle(HScrollView.class.getSimpleName());
    }
}
