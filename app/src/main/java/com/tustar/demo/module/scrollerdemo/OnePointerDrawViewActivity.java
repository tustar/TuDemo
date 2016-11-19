package com.tustar.demo.module.scrollerdemo;

import android.os.Bundle;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

public class OnePointerDrawViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pointer_draw_view);
        setTitle(OnePointerDrawView.class.getSimpleName());
    }
}
