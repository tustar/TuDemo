package com.tustar.demo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseDemoListAdapter;
import com.tustar.demo.db.test.DemoItem;

import java.util.List;


/**
 * @author amit
 */
public class DemoGridAdapter extends BaseDemoListAdapter {

    public DemoGridAdapter(Context context) {
        super(context);
    }

    public DemoGridAdapter(Context context, List<DemoItem> items) {
        super(context, items);
    }

    @Override
    protected DemoViewHolder createViewHolder(ViewGroup parent) {
        return new DemoViewHolder(R.layout.item_grid, parent, mInflater);
    }
}
