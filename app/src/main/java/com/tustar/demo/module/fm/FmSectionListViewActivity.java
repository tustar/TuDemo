package com.tustar.demo.module.fm;

import android.os.Bundle;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;

public class FmSectionListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_section_list_view);
        setTitle(R.string.fm_section_list_view);
    }
}
