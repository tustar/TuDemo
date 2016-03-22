package com.tustar.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tustar.demo.widget.dslv.DragSortListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DragSortListViewActivity extends AppCompatActivity {

    private static final String TAG = DragSortListViewActivity.class.getSimpleName();
    private Context mContext;
    private DragSortAdapter mAdatpter;
    private ArrayList<String> mDatas = new ArrayList<>();
    private DragSortListView mListView;

    // Drag
    private DragSortListView.DropListener mDropListner = new DragSortListView.DragSortListener() {
        @Override
        public void drag(int from, int to) {

        }

        @Override
        public void drop(int from, int to) {
            if (from != to) {
                String item = mAdatpter.getItem(from);
                mAdatpter.remove(item);
                mAdatpter.insert(item, to);
            }
        }

        @Override
        public void remove(int which) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_sort_list_view);
        mContext = this;

        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.content_data)));
        mListView = (DragSortListView) findViewById(R.id.dslv_listView);
        mListView.setDropListener(mDropListner);
        mAdatpter = new DragSortAdapter(mContext, R.layout.item_drag_sort,
                R.id.dslv_item_text, mDatas);
        mListView.setAdapter(mAdatpter);
    }

    class DragSortAdapter extends ArrayAdapter<String> {


        public DragSortAdapter(Context context, int resource,
                               int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            ItemDragSort item = null;
            if (convertView != view && view != null) {
                item = new ItemDragSort(view);
                view.setTag(item);
            }

            item = (ItemDragSort) view.getTag();
            item.mDslvText.setText(getItem(position));

            return view;
        }
    }

    class ItemDragSort {

        TextView mDslvText;
        ImageView mDslvDragHandle;

        public ItemDragSort(View convertView) {
            mDslvText = (TextView) convertView.findViewById(R.id.dslv_item_text);
            mDslvDragHandle = (ImageView) convertView.findViewById(R.id.dslv_item_drag_handle);
        }
    }
}
