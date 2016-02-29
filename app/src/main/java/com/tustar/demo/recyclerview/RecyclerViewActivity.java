package com.tustar.demo.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tustar.demo.R;
import com.tustar.demo.adapter.ReRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements ReRecyclerViewAdapter.OnItemClickLitener {

    private RecyclerView mRecyclerView;
    private ReRecyclerViewAdapter mAdapter;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        for (int i = 0; i < 100; i++) {
            mData.add(i + "");
        }
        mAdapter = new ReRecyclerViewAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, view + "" + position, Toast.LENGTH_SHORT).show();
    }
}
