package com.tustar.demo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tustar.util.ToastUtils;
import com.tustar.qyz.db.DemoItem;

import java.util.List;


/**
 * @author amit
 */
public abstract class BaseDemoListAdapter extends BaseAdapter {

    private static final int COLOR_VIEW_TYPE = 0;

    protected List<DemoItem> mItems;
    protected LayoutInflater mInflater;

    public BaseDemoListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public BaseDemoListAdapter(Context context, List<DemoItem> items) {
        mItems = items;
        mInflater = LayoutInflater.from(context);
    }

    public void setItems(List items) {
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DemoViewHolder holder;
        if (convertView == null) {
            holder = createViewHolder(parent);
            holder.getView().setTag(holder);
        } else {
            holder = (DemoViewHolder) convertView.getTag();
        }

        holder.init((DemoItem) getItem(position));
        return holder.getView();
    }

    protected abstract DemoViewHolder createViewHolder(ViewGroup parent);

    @Override
    public int getItemViewType(int position) {
        return COLOR_VIEW_TYPE;
    }

    public static class DemoViewHolder {

        TextView mTextView;

        public DemoViewHolder(int resId, ViewGroup parent, LayoutInflater inflater) {
            mTextView = (TextView) inflater.inflate(resId, parent, false);
            final Context context = mTextView.getContext();
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.show(context, "Tapped on: " + mTextView.getText(), Toast.LENGTH_SHORT);
                }
            });
            mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ToastUtils.show(context, "Long-tapped on: " + mTextView.getText(), Toast.LENGTH_SHORT);
                    return false;
                }
            });
        }

        public void init(DemoItem item) {
            mTextView.setText(item.getColorName());
            int color = item.getColor();
            mTextView.setBackgroundColor(color);
        }

        public View getView() {
            return mTextView;
        }
    }
}
