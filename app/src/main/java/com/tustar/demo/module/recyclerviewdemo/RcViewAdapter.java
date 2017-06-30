package com.tustar.demo.module.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tustar.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tustar on 2/29/16.
 */
public class RcViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RcViewAdapter.class.getSimpleName();
    private List<String> mData = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public RcViewAdapter(List<String> data) {
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RcViewHolder reViewHolder = (RcViewHolder) holder;
        reViewHolder.mRcTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickLitener) {
        this.onItemClickListener = onItemClickLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // BindingHolder
    class RcViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mRcTextView;

        public RcViewHolder(View itemView) {
            super(itemView);
            mRcTextView = (TextView) itemView.findViewById(R.id.rc_item_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
