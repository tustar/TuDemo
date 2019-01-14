package com.tustar.demo.ui.recycler;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tustar.common.util.Logger;
import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.ui.qyz.overscroll.OverScrollDecoratorHelper;

import java.util.Arrays;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tustar.demo.ui.qyz.overscroll.OverScrollDecoratorHelper.ORIENTATION_VERTICAL;
import static com.tustar.demo.ui.recycler.RcViewAdapter.OnItemClickListener;

public class RecyclerViewActivity extends BaseActivity implements OnItemClickListener {

    private static final String TAG = RecyclerViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RcViewAdapter mAdapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.i(TAG, "onCreate ::");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.re_recyclerview);
        // Layout Manager
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Adapter

        mAdapter = new RcViewAdapter(Arrays.asList(getResources().getStringArray(
                R.array.content_data)));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(final View view, int position) {
                Logger.i(TAG, "onItemClick :: view = " + view + ", position = " + position);
                view.animate()
                        .translationZ(15.0f)
                        .setDuration(200)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                view.animate()
                                        .translationZ(1.0f)
                                        .setDuration(500)
                                        .start();
                            }
                        })
                        .start();
            }
        });
        OverScrollDecoratorHelper.setUpOverScroll(mRecyclerView, ORIENTATION_VERTICAL);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, view + "" + position, Toast.LENGTH_SHORT).show();
    }
}
