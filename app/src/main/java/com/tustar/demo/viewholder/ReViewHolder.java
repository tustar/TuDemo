package com.tustar.demo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tustar.demo.R;

/**
 * Created by tustar on 2/29/16.
 */
public class ReViewHolder extends RecyclerView.ViewHolder {

    public TextView mReTextView;

    public ReViewHolder(View itemView) {
        super(itemView);
        mReTextView = (TextView)itemView.findViewById(R.id.re_item_textview);
    }
}
