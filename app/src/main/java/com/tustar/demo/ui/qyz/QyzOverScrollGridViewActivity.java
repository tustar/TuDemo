package com.tustar.demo.ui.qyz;

import android.os.Bundle;
import android.widget.GridView;

import com.tustar.demo.R;
import com.tustar.demo.adapter.DemoGridAdapter;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.db.test.DemoContentHelper;
import com.tustar.demo.db.test.DemoItem;
import com.tustar.demo.ui.qyz.overscroll.OverScrollDecoratorHelper;

import java.util.List;

public class QyzOverScrollGridViewActivity extends BaseActivity {

    private static final String TAG = "QyzOverScrollGridViewActivity";
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qyz_over_scroll_grid_view);
        setTitle(R.string.qyz_over_scroll_title);

        mGridView = (GridView) findViewById(R.id.over_scroll_grid_view);
        List<DemoItem> items = DemoContentHelper.getSpectrumItems(getResources());
        DemoGridAdapter adapter = new DemoGridAdapter(this, items);
        mGridView.setAdapter(adapter);

        OverScrollDecoratorHelper.setUpOverScroll(mGridView);
    }
}
