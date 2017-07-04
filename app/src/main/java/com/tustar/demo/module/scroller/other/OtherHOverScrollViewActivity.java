package com.tustar.demo.module.scroller.other;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tustar.demo.R;

public class OtherHOverScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hover_scroll_view);
        setTitle(OtherHOverScrollView.class.getSimpleName());
    }
}
