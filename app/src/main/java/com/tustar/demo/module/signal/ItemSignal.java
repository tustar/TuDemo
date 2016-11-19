package com.tustar.demo.module.signal;

import android.view.View;
import android.widget.TextView;

import com.tustar.demo.R;


/**
 * Created by tustar on 16-2-19.
 */
public class ItemSignal {

    public TextView mRfSignalSim;
    public TextView mRfSignalNetwork;
    public TextView mRfSignalProp1;
    public TextView mRfSignalProp2;

    public ItemSignal(View view) {
        mRfSignalSim = (TextView) view.findViewById(R.id.rf_signal_sim);
        mRfSignalNetwork = (TextView) view.findViewById(R.id.rf_signal_network);
        mRfSignalProp1 = (TextView) view.findViewById(R.id.rf_signal_prop1);
        mRfSignalProp2 = (TextView) view.findViewById(R.id.rf_signal_prop2);
    }
}
