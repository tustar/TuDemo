package com.tustar.demo.ui.custom;

import android.os.Bundle;

import com.tustar.demo.R;

import androidx.appcompat.app.AppCompatActivity;

public class HOverScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover_scroll_view);
        setTitle(HOverScrollView.class.getSimpleName());
    }
}
