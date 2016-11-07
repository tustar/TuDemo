package com.tustar.demo.activity.deskclock;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tustar.demo.BR;
import com.tustar.demo.R;
import com.tustar.demo.provider.History;
import com.tustar.demo.activity.deskclock.AlarmClockFragment.OnListFragmentInteractionListener;

import java.util.List;

import static android.support.v7.recyclerview.R.styleable.RecyclerView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link History} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AlarmClockRecyclerViewAdapter extends RecyclerView.Adapter<AlarmClockRecyclerViewAdapter.BindingHolder> {

    private final List<History> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AlarmClockRecyclerViewAdapter(List<History> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

//    @Override
//    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_alarmclock_item, parent, false);
//        return new BindingHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final BindingHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(String.valueOf(mValues.get(position).id));
//        holder.mContentView.setText(String.valueOf(mValues.get(position).createdAt));
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
//    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.fragment_alarmclock_item,
                parent,
                false);
        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        History history = mValues.get(position);
        holder.getBinding().setVariable(BR.history, history);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

//    public class BindingHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public History mItem;
//
//        public BindingHolder(View view) {
//            super(view);
//            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }

    public class BindingHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public BindingHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}
