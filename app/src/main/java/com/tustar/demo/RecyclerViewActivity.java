package com.tustar.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tustar.demo.adapter.RcViewAdapter;
import com.tustar.demo.utils.Logger;

import java.util.Arrays;

import static com.tustar.demo.adapter.RcViewAdapter.*;

public class RecyclerViewActivity extends BaseActivity implements OnItemClickListener {

    private static final String TAG = RecyclerViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RcViewAdapter mAdapter;

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Logger.d(TAG, "onOptionsItemSelected :: id = " + id);
        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, view + "" + position, Toast.LENGTH_SHORT).show();
    }
}
