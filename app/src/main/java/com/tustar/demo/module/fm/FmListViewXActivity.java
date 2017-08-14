package com.tustar.demo.module.fm;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ListViewX;
import android.widget.ListViewX.OnSlideListener;
import android.widget.ListViewX.OnSlideLoadingStateListener;
import android.widget.ListViewX.OnSlideOutItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.tustar.demo.R;
import com.tustar.demo.module.ryg.ch2.utils.PreferenceHelper;
import com.tustar.demo.module.ryg.ch2.utils.Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FmListViewXActivity extends Activity {

    private static final int NAME_TAG = 0x11000000;
    private static final int LIST_LENGTH = 30;
    private static final int LIST_MODE_NORMAL_ITEM = 0;
    private static final int LIST_MODE_MORE_ITEM = 1;
    private static final int LIST_MODE_LESS_ITEM = 2;
    private static final int LIST_MODE_CHANGE_ITEM = 3;
    private static final int LIST_MODE_MAX = 4;

    private List<Map<String, String>> mData;
    private SimpleAdapter mAdapter;
    private ListViewX mListView;
    private int mListViewItemMode = LIST_MODE_NORMAL_ITEM;
    private String mItemText;
    private String mChangedItemText;
    
    private int mSelectedPosition = 0;
    private void addData() {
        Map<String, String> item  = new HashMap<String, String>();
        if (mListViewItemMode == LIST_MODE_CHANGE_ITEM) {
            item.put("title", new String(mChangedItemText + mSelectedPosition));
        } else {
            item.put("title", new String(mItemText + mSelectedPosition));
        }
        mData.add(mSelectedPosition, item);
        mAdapter.notifyDataSetChanged();
        mListView.startAddAnimation(mSelectedPosition);
    }

    private Runnable mAddDataEvent = new Runnable() {
       public void run() {
           addData();
       }
    };

    protected void onCreate(Bundle savedInstanceState) {
        setAppTheme(this, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_list_view_x);

        mItemText = getResources().getString(R.string.fm_list_item_text);
        mChangedItemText = getResources().getString(R.string.fm_changed_list_item_text);
        mListView = (ListViewX)findViewById(R.id.list);

        mData = new ArrayList<>();

        for (int i = 0; i < LIST_LENGTH; i++) {
            Map<String, String> item  = new HashMap<String, String>();
            item.put("title", new String(mItemText + i));
            mData.add(item);
        }

        mAdapter = new SimpleAdapter(this, mData,
                android.R.layout.simple_list_item_1,
                new String[] {"title"},
                new int[] {android.R.id.text1});
//        mListView.setListItemMode(ListViewX.LIST_MODE_NORMAL);
        mListView.setAdapter(mAdapter);
//        DatabaseForTest db = new DatabaseForTest(this);
//        mListView.setAdapter(new ListViewCursorAdapterFor2Items(this, db.getAllRecords()));
//        mListView.setPullAnimEnabled(false);
        mListView.setListItemMode(ListViewX.LIST_MODE_LEFT_SLIDE_SHOW_ITEM |
                ListViewX.LIST_MODE_RIGHT_SLIDE | ListViewX.LIST_MODE_LONG_PRESS_FLOAT_MENU);
//                ListViewX.LIST_MODE_LONG_PRESS_EDIT_MODE | ListViewX.LIST_MODE_RESERVE_LONG_PRESS |
//                ListViewX.LIST_MODE_SPLIT_SLIDE_VIEW);
//        mListView.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
//                getResources().getStringArray(R.array.LongListItems)));
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(
                        FmListViewXActivity.this,
                        "List item " + position + " is selected",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        });

        mListView.setOnSlideOutItemClickListener(new OnSlideOutItemClickListener() {

            @Override
            public void onSlideOutItemClick(View view, int position, long id) {
                int item = (int)mListView.getWrappedAdapter().getItemId(position);
                Object tag = view.getTag(NAME_TAG);
                String tagStr = null;
                if ((tag != null) && (tag instanceof String)) {
                    tagStr = (String)tag;
                }
                Toast.makeText(FmListViewXActivity.this,
                        "Delete is selected:position is " + position + " id is " + item + " tag=" + tagStr,
                        Toast.LENGTH_SHORT).show();
                mData.remove(position);
                mAdapter.notifyDataSetChanged();
                mListView.startRemoveAnimation(position);
                mSelectedPosition = position;
                new Handler().postDelayed(mAddDataEvent, 2000);
            }
        });

        mListView.setOnSlideListener(new OnSlideListener() {

            @Override
            public void onSlide(View view, int position, int status) {
//                Toast.makeText(ListViewXDebugTest.this,
//                        "Item " + position + " status is " + status,
//                        Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnEditModeStateListener(new ListViewX.OnEditModeStateListener() {

            @Override
            public void onExitEditMode(ListView list, boolean[] checkedItems) {
                Toast.makeText(FmListViewXActivity.this,
                        "exit edit mode", Toast.LENGTH_SHORT)
                        .show();
//                mListView.setLeftButtonEnabledInEditMode(false);
//                Runnable shower = new Runnable() {
//                    public void run() {
//                        mListView.setLeftButtonEnabledInEditMode(true);
//                    }
//                };
//                mListView.postDelayed(shower, 6000);
//                SparseBooleanArray items = mListView.getCheckedItemPositions();
//                for (int i = 0; i < mListView.getCount(); i++) {
//                    Log.d("FmListViewXActivity", "Item " + i + " is checked:" + items.get(i));
//                }
            }

            @Override
            public void onItemCheckedChanged(ListView arg0, int position,
                    long id, boolean checked) {
//                Toast.makeText(FmListViewXActivity.this,
//                        "Item " + position + " is checked:" + checked + " id is " + id,
//                        Toast.LENGTH_SHORT)
//                        .show();
            }

            @Override
            public void onMultiItemCheckedChanged(ListView arg0, boolean[] checkedItems) {
//                Toast.makeText(FmListViewXActivity.this,
//                        "More than one items are selected or unselected:" + checkedItems.toString(),
//                        Toast.LENGTH_SHORT)
//                        .show();
            }

            @Override
            public void onPrepareEditMode(ListView list, boolean[] checkedItems) {
//              checkedItems[0] = true;
//              checkedItems[2] = true;
//              checkedItems[3] = true;
            }

            @Override
            public String getCustomizedTitle(ListView list, int checkedItemCount) {
//                if (checkedItemCount == 0) {
//                    return getResources().getString(R.string.listview_x_test_no_photo_selected);
//                } else {
//                    return getResources().getString(R.string.listview_x_test_photo_selected, checkedItemCount);
//                }
                return null;
            }

            @Override
            public void prepareCustomizedTitle(ListView list, View titleView) {
//                titleView.setBackgroundColor(Color.BLUE);
            }
        });

        mListView.setOnSlideLoadingStateListener(new OnSlideLoadingStateListener() {

            @Override
            public void onSlideLoadingState(View view, int state) {
                if (state == ListViewX.STATE_HEADER_VIEW_SHOW) {
                    Toast.makeText(FmListViewXActivity.this,
                            "Data update start!",
                            Toast.LENGTH_SHORT)
                            .show();
                    FmListViewXActivity.this.updateListViewData();
                }
            }
        });


        mListView.setFloatMenuItems(R.array.fm_float_list, new ListViewX.OnFloatMenuOnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which, int position) {
                Toast.makeText(FmListViewXActivity.this,
                        "Float menu item " + which + " is clicked. List position is " + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        Button testBtn = new Button(this);
        testBtn.setText("test footer view");
        testBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Map<String, String> item  = new HashMap<String, String>();
//                item.put("title", new String(mItemText + mData.size()));
//                mData.add(item);
//                mAdapter.notifyDataSetChanged();
            }
        });
//        mListView.showDividerInEditMode(false);
//        mListView.addFooterView(testBtn);
        Button testBtn2 = new Button(this);
        testBtn2.setText("test header view");
        mListView.addHeaderView(testBtn2);
        View headerView = getLayoutInflater().inflate(R.layout.item_fm_header_view, null);
        mListView.addHeaderView(headerView);
//        mListView.setDivider(getResources().getDrawable(R.drawable.tabs_divider));

//        registerForContextMenu(mListView);

//        mListView.startEditMode();
//        mListView.setLeftTextColorInEditMode(getResources().getColorStateList(R.color.bottom_tabbar_text_color));
//        mListView.setRightTextColorInEditMode(getResources().getColorStateList(R.color.bottom_toolbar_text_color));
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                    int position, long id) {
//                Toast.makeText(FmListViewXActivity.this,
//                        "item is long clicked", Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });

        Button btn = (Button)findViewById(R.id.btn_control);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final int count = mListView.getCheckedItemCount();
                StringBuffer s = new StringBuffer("Totally ");
                s.append(count)
                    .append(" items are selected.");
                if (count > 0) {
                    s.append(" And they are:")
                        .append(mListView.getCheckedItemPositions().toString());
                }
                Toast.makeText(FmListViewXActivity.this,
                        s.toString(), Toast.LENGTH_LONG)
                        .show();
            }

        });

        Button btnStartEdit = (Button)findViewById(R.id.btn_start_edit_mode);
        btnStartEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListView.startEditMode();
            }

        });

        Button btnStopEdit = (Button)findViewById(R.id.btn_stop_edit_mode);
        btnStopEdit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListView.stopEditMode();
            }

        });

        super.onCreate(savedInstanceState);

//        addBlur();
//        addBlue2();
        mListView.startEditMode();
    }

    public ListViewX getListView() {
        return mListView;
    }

    public void updateListViewData() {
        mListViewItemMode++;
        if (mListViewItemMode == LIST_MODE_MAX) {
            mListViewItemMode = LIST_MODE_NORMAL_ITEM;
        }
        switch (mListViewItemMode) {
            case LIST_MODE_NORMAL_ITEM: {
                mData.clear();
                for (int i = 0; i < LIST_LENGTH; i++) {
                    Map<String, String> item  = new HashMap<String, String>();
                    item.put("title", new String(mItemText + i));
                    mData.add(item);
                }
                break;
            }
            case LIST_MODE_MORE_ITEM: {
                mData.clear();
                for (int i = 0; i < LIST_LENGTH * 2; i++) {
                    Map<String, String> item  = new HashMap<String, String>();
                    item.put("title", new String(mItemText + i));
                    mData.add(item);
                }
                break;
            }
            case LIST_MODE_LESS_ITEM: {
                mData.clear();
                for (int i = 0; i < LIST_LENGTH / 2; i++) {
                    Map<String, String> item  = new HashMap<String, String>();
                    item.put("title", new String(mItemText + i));
                    mData.add(item);
                }
                break;
            }
            case LIST_MODE_CHANGE_ITEM: {
                mData.clear();
                for (int i = 0; i < LIST_LENGTH; i++) {
                    Map<String, String> item  = new HashMap<String, String>();
                    item.put("title", new String(mChangedItemText + i));
                    mData.add(item);
                }
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
//        menu.setHeaderTitle(R.string.menu_title);
//        menu.add(R.string.menu_item_1);
//        menu.add(R.string.menu_item_2);
//        menu.add(R.string.menu_item_3);
        super.onCreateContextMenu(menu, v, menuInfo);
    }


//    private void addBlur(){
//
//        /* get Wallpaper drawable */
//        WallpaperManager wm = WallpaperManager.getInstance(this.getApplicationContext());
//        BitmapDrawable bdSrc = (BitmapDrawable) wm.getDrawable();
//        /* apply gaussian blur */
//
//        BitmapDrawable bdDst = ZuiBlur.generateBlackMaskDrawable(this, bdSrc);
//        //BitmapDrawable bdDst = ZuiBlur.generateBlurDrawableByDefault(this /* Context */, bdSrc, ZuiBlur.BLUR_TYPE_GAUSSIAN);
//        /* set background */
//        getWindow().setBackgroundDrawable(bdDst);
//
//    }
//
//    private void addBlue2() {
//        /* get Wallpaper drawable */
//        WallpaperManager wm = WallpaperManager.getInstance(this /* Context */);
//        BitmapDrawable bdSrc = (BitmapDrawable) wm.getDrawable();
//
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//
//        int wallPaperWidth = bdSrc.getBitmap().getWidth();
//        int wallPaperHeight = bdSrc.getBitmap().getHeight();
//
//        if(wallPaperWidth > wallPaperHeight){
//           wallPaperWidth = width * wallPaperHeight / height;
//
//        }else{
//           wallPaperHeight = width * wallPaperWidth / height;
//        }
//
//        Bitmap bitMap = Bitmap.createBitmap(bdSrc.getBitmap(), bdSrc
//              .getBitmap().getWidth() / 2 - wallPaperWidth / 2, 0,
//              wallPaperWidth, wallPaperHeight);
//        BitmapDrawable bdDst = ZuiBlur.generateBlackMaskDrawable(this,
//              new BitmapDrawable(bitMap));
//        getWindow().setBackgroundDrawable(bdDst);
//     }

    public static int setAppTheme(Activity activity, Bundle savedInstanceState) {
        int currentTheme;
        if (savedInstanceState == null) {
            currentTheme = PreferenceHelper.getTheme(activity);
        } else {
            currentTheme = savedInstanceState.getInt("theme");
        }
        activity.setTheme(currentTheme);
        int statusbarIconColor = Color.BLACK;
        if (currentTheme == android.R.style.Theme_DeviceDefault) {
            activity.getWindow().setStatusBarColor(
                    activity.getResources().getColor(R.color.status_bar_bg));
            statusbarIconColor = activity.getResources().getColor(R.color.status_bar_icon_color);
        } else if (currentTheme == android.R.style.Theme_DeviceDefault_Light) {
            activity.getWindow().setStatusBarColor(
                    activity.getResources().getColor(R.color.status_bar_bg_light));
            statusbarIconColor = activity.getResources().getColor(R.color.status_bar_icon_color_light);
//            // show dark icon
//            try{
//                Method setM = Window.class.getDeclaredMethod("setDarkStatusIcon", boolean.class);
//                setM.invoke(activity.getWindow(),true);
//             }catch(Exception e){
//
//             }
        }
        // set icon color
        try{
            Method setM = Window.class.getDeclaredMethod("setStatusBarIconColor", int.class);
            setM.invoke(activity.getWindow(),statusbarIconColor);
        } catch(Exception e) {

        }

        return currentTheme;
    }
}
