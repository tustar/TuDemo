package com.tustar.demo.module.scroller;

import android.os.Bundle;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

public class TwoPointerDrawViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_pointer_draw_view);
        setTitle(TwoPointerDrawView.class.getSimpleName());
    }
}
