package com.tustar.demo.module.qyz;

import android.os.Bundle;
import android.widget.GridView;

import com.tustar.demo.R;
import com.tustar.demo.adapter.DemoGridAdapter;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.db.test.DemoContentHelper;
import com.tustar.demo.db.test.DemoItem;
import com.tustar.demo.module.qyz.overscroll.OverScrollDecoratorHelper;

import java.util.List;

public class OverScrollGridViewActivity extends BaseActivity {

    private static final String TAG = "OverScrollGridViewActivity";
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_scroll_grid_view);

        mGridView = (GridView) findViewById(R.id.over_scroll_grid_view);
        List<DemoItem> items = DemoContentHelper.getSpectrumItems(getResources());
        DemoGridAdapter adapter = new DemoGridAdapter(this, items);
        mGridView.setAdapter(adapter);

        OverScrollDecoratorHelper.setUpOverScroll(mGridView);
    }
}
