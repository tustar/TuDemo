package com.tustar.demo.module.scrollerdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.common.ListViewDecoration;
import com.tustar.demo.module.scrollerdemo.other.OtherHOverScrollView;
import com.tustar.demo.module.scrollerdemo.other.OtherHOverScrollViewActivity;
import com.tustar.demo.module.scrollerdemo.other.OtherHScrollView;
import com.tustar.demo.module.scrollerdemo.other.OtherHScrollViewActivity;
import com.tustar.demo.module.scrollerdemo.other.OtherOnePointerDrawView;
import com.tustar.demo.module.scrollerdemo.other.OtherOnePointerDrawViewActivity;
import com.tustar.demo.module.scrollerdemo.other.OtherTwoPointerDrawView;
import com.tustar.demo.module.scrollerdemo.other.OtherTwoPointerDrawViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollerActivity extends BaseActivity implements ScrollerAdapter.OnItemClickListener {

    private Context mContext;
    @BindView(R.id.scroller_recyclerview)
    RecyclerView mScrollerRecyclerview;
    @BindView(R.id.activity_scroller)
    RelativeLayout mActivityScroller;

    private ScrollerAdapter mAdapter;

    private static ArrayList<Class> sClassList = new ArrayList<>();

    // List
    static {
        sClassList.add(OnePointerDrawViewActivity.class);
        sClassList.add(TwoPointerDrawViewActivity.class);
        sClassList.add(HScrollViewActivity.class);
        sClassList.add(HOverScrollViewActivity.class);
        //
        sClassList.add(OtherOnePointerDrawViewActivity.class);
        sClassList.add(OtherTwoPointerDrawViewActivity.class);
        sClassList.add(OtherHScrollViewActivity.class);
        sClassList.add(OtherHOverScrollViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);
        setTitle(ScrollerActivity.class.getSimpleName());
        mContext = this;
        ButterKnife.bind(this);

        List<String> datas = new ArrayList<>();
        datas.add(OnePointerDrawView.class.getSimpleName());
        datas.add(TwoPointerDrawView.class.getSimpleName());
        datas.add(HScrollView.class.getSimpleName());
        datas.add(HOverScrollView.class.getSimpleName());
        //
        datas.add(OtherOnePointerDrawView.class.getSimpleName());
        datas.add(OtherTwoPointerDrawView.class.getSimpleName());
        datas.add(OtherHScrollView.class.getSimpleName());
        datas.add(OtherHOverScrollView.class.getSimpleName());
        mAdapter = new ScrollerAdapter(datas);
        mScrollerRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        mScrollerRecyclerview.setHasFixedSize(true);
        mScrollerRecyclerview.addItemDecoration(new ListViewDecoration(mContext));
        mAdapter.setOnItemClickListener(this);
        mScrollerRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        Class<?> clazz = sClassList.get(position);
        if (null != clazz) {
            intent.setClass(this, clazz);
            startActivity(intent);
        }
    }
}
