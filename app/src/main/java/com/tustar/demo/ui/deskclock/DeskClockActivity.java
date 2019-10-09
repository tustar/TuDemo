package com.tustar.demo.ui.deskclock;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tustar.demo.R;
import com.tustar.demo.base.BaseActivity;
import com.tustar.demo.ui.deskclock.provider.History;

import androidx.viewpager.widget.ViewPager;

public class DeskClockActivity extends BaseActivity implements AlarmClockFragment.OnListFragmentInteractionListener {

    ViewPager mDeskViewpager;
    TabLayout mDeskTabs;

    private DeskClockFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk_clock);
        setTitle(R.string.deskclock_title);
        mDeskViewpager = findViewById(R.id.desk_viewpager);
        mDeskTabs = findViewById(R.id.desk_tabs);

        mPagerAdapter = new DeskClockFragmentPagerAdapter(getSupportFragmentManager(), this);
        mDeskViewpager.setAdapter(mPagerAdapter);
        mDeskTabs.setupWithViewPager(mDeskViewpager);
        mDeskTabs.setTabMode(TabLayout.MODE_FIXED);
        int tabCount = mDeskTabs.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tab = mDeskTabs.getTabAt(i);
            tab.setCustomView(mPagerAdapter.getTabView(i));
        }
    }

    @Override
    public void onListFragmentInteraction(History item) {

    }
}
