package com.tustar.demo.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tustar.demo.R;
import com.tustar.demo.adapter.ReRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import com.tustar.demo.utils.Logger;

public class RecyclerViewActivity extends AppCompatActivity implements ReRecyclerViewAdapter.OnItemClickLitener {

    private static final String TAG = RecyclerViewActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private ReRecyclerViewAdapter mAdapter;
    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
