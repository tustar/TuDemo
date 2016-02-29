package com.tustar.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tustar.demo.R;
import com.tustar.demo.viewholder.ReViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tustar on 2/29/16.
 */
public class ReRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> data = new ArrayList<>();

    private OnItemClickLitener mOnItemClickLitener;

    public ReRecyclerViewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new ReViewHolder(View.inflate(context, R.layout.item_recycler_view, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReViewHolder reViewHolder = (ReViewHolder)holder;
        reViewHolder.mReTextView.setText("" + position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }
}
