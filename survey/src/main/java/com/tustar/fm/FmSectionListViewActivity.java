package com.tustar.fm;

import android.os.Bundle;

import com.tustar.fm.R;
import com.tustar.fm.BaseActivity;

public class FmSectionListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_section_list_view);
        setTitle(R.string.fm_section_list_view);
    }
}