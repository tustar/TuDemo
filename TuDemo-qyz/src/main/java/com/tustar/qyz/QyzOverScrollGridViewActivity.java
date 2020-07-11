package com.tustar.qyz;

import android.os.Bundle;
import android.widget.GridView;

import com.tustar.qyz.db.DemoContentHelper;
import com.tustar.qyz.db.DemoItem;
import com.tustar.qyz.overscroll.OverScrollDecoratorHelper;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class QyzOverScrollGridViewActivity extends AppCompatActivity {

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
