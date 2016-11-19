package com.tustar.demo.module.loaderdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tustar.demo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tustar on 5/14/16.
 */
public class AppListAdapter extends ArrayAdapter<AppEntry> {

    private LayoutInflater mInflater;

    public AppListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_loader_app, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppEntry entry = getItem(position);
        holder.mIcon.setImageDrawable(entry.getIcon());
        holder.mLabel.setText(entry.getLabel());
        holder.mSize.setText(entry.getApkFile().length() / 1024 / 1024 + "M");
        holder.mLastUpdateTime.setText(new SimpleDateFormat("mm-dd hh:MM").format(
                new Date(entry.getApkFile().lastModified())));

        return convertView;
    }

    public void setData(List<AppEntry> data) {
        clear();
        if (null != data) {
            addAll(data);
        }
    }

    class ViewHolder {

        public ImageView mIcon;
        public TextView mLabel;
        public TextView mSize;
        public TextView mLastUpdateTime;

        ViewHolder(View convertView) {
            mIcon = (ImageView) convertView.findViewById(R.id.item_loader_app_icon);
            mLabel = (TextView) convertView.findViewById(R.id.item_loader_app_label);
            mSize = (TextView) convertView.findViewById(R.id.item_loader_app_size);
            mLastUpdateTime = (TextView) convertView.findViewById(
                    R.id.item_loader_app_last_updatetime);
        }
    }
}
