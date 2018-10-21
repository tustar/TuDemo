package com.tustar.demo.module.scroller.other;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.tustar.demo.R;

public class OtherHScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscroll_view);
        setTitle(OtherHScrollView.class.getSimpleName());
    }
}
