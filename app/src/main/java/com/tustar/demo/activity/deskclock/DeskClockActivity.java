package com.tustar.demo.activity.deskclock;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tustar.demo.R;
import com.tustar.demo.activity.BaseActivity;
import com.tustar.demo.provider.History;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeskClockActivity extends BaseActivity implements AlarmClockFragment.OnListFragmentInteractionListener {

    @BindView(R.id.desk_viewpager)
    ViewPager mDeskViewpager;
    @BindView(R.id.desk_tabs)
    TabLayout mDeskTabs;

    private DeskClockFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk_clock);
        ButterKnife.bind(this);

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
