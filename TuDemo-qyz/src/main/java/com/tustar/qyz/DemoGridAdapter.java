package com.tustar.qyz;

import android.content.Context;
import android.view.ViewGroup;

import com.tustar.qyz.db.DemoItem;

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
