package com.tustar.demo.module.scrollerdemo.other;

import android.os.Bundle;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.module.scrollerdemo.OnePointerDrawView;

public class OtherOnePointerDrawViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_pointer_draw_view);
        setTitle(OtherOnePointerDrawView.class.getSimpleName());
    }
}