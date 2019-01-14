package com.tustar.demo.ui.signal;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tustar.demo.R;

import java.util.List;


/**
 * Created by tustar on 16-2-16.
 */
public class PhoneStateFloatView extends LinearLayout {

    private Context mContext;
    private ListView mRfSignalListView;
    private SignalInfoAdapter mAdapter;

    public PhoneStateFloatView(Context context, List<SignalInfo> infos) {
        super(context);
        init(context, infos);
    }

    private void init(Context context, List<SignalInfo> infos) {

        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_float_view, this);

        // UI
        mRfSignalListView = (ListView) findViewById(R.id.rf_signal_listview);
        mAdapter = new SignalInfoAdapter(context, infos);
        mRfSignalListView.setAdapter(mAdapter);
    }

    public void updateSignalInofs(List<SignalInfo> infos) {
        mAdapter.setmInfos(infos);
    }
}
