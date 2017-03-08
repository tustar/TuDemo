package com.tustar.demo.module.qyz;


import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.module.qyz.widget.FlexibleListView;

public class FlexibleListViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible_list_view);

        initView();
    }

    private void initView() {

        FlexibleListView listview = (FlexibleListView) findViewById(R.id.flex_list_view);
        String[] contents = getResources().getStringArray(R.array.content_data);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contents);
        listview.setAdapter(adapter);
    }
}
