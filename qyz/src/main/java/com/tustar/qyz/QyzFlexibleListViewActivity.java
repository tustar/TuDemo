package com.tustar.qyz;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tustar.qyz.R;
import androidx.appcompat.app.AppCompatActivity;
import com.tustar.qyz.overscroll.OverScrollDecoratorHelper;

public class QyzFlexibleListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_flexible_list_view);
        setTitle(R.string.qyz_flex_title);

        initView();
    }

    private void initView() {

        ListView listview = (ListView) findViewById(R.id.flex_list_view);
        String[] contents = getResources().getStringArray(R.array.content_data);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contents);
        listview.setAdapter(adapter);

        OverScrollDecoratorHelper.setUpOverScroll(listview);
    }
}
