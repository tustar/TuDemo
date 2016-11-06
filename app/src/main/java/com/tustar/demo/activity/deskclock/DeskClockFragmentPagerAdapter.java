package com.tustar.demo.activity.deskclock;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tustar.demo.R;

/**
 * Created by tustar on 11/6/16.
 */
public class DeskClockFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"闹钟", "世界时钟", "倒计时", "秒表"};
    private int[] imageResId = {
            R.drawable.ic_tab_alarm,
            R.drawable.ic_tab_clock,
            R.drawable.ic_tab_timer,
            R.drawable.ic_tab_stopwatch};
    private Context context;

    public DeskClockFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return AlarmClockFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.deskclock_tab_item, null);
        TextView tv = (TextView) view.findViewById(R.id.tab_title);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        img.setImageResource(imageResId[position]);
        return view;
    }
}
