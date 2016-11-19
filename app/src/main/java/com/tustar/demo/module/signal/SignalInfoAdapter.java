package com.tustar.demo.module.signal;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tustar.demo.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tustar on 16-2-19.
 */
public class SignalInfoAdapter extends BaseAdapter {

    private Context mContext;
    private List<SignalInfo> mInfos = new ArrayList<>();
    private LayoutInflater mInflater;

    public SignalInfoAdapter(Context context, List<SignalInfo> infos) {
        this.mContext = context;
        this.mInfos = infos;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return null == mInfos ? 0 : mInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemSignal item;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_signal, parent, false);
            item = new ItemSignal(convertView);
            convertView.setTag(item);
        } else {
            item = (ItemSignal) convertView.getTag();
        }

        SignalInfo info = mInfos.get(position);
        updateSignalText(item.mRfSignalSim, info.getSim());
        updateSignalText(item.mRfSignalNetwork, info.getNetwork());
        updateSignalText(item.mRfSignalProp1, info.getProp1());
        updateSignalText(item.mRfSignalProp2, info.getProp2());

        return convertView;
    }

    public void setmInfos(List<SignalInfo> infos) {
        this.mInfos = infos;
        notifyDataSetChanged();
    }

    private void updateSignalText(TextView textView, String newText) {

        String oldText = textView.getText().toString();
        if (TextUtils.isEmpty(oldText)) {
            return;
        }

        if (TextUtils.isEmpty(newText)) {
            return;
        }

        if (oldText.equals(newText)) {
            return;
        }

        textView.setText(newText);
        int redLightColor = mContext.getResources().getColor(android.R.color.holo_red_light);
        int greenLightColor = mContext.getResources().getColor(android.R.color.holo_green_light);
        int blueLightColor = mContext.getResources().getColor(android.R.color.holo_blue_bright);
        int currentColor = textView.getCurrentTextColor();
        if (redLightColor == currentColor) {
            textView.setTextColor(greenLightColor);
        } else if (greenLightColor == currentColor) {
            textView.setTextColor(blueLightColor);
        } else if (blueLightColor == currentColor) {
            textView.setTextColor(redLightColor);
        }
    }
}
