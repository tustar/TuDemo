package com.tustar.demo.ui.optimize

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabPagerAdapter(
    @NonNull fm: FragmentManager,
    private val pagerInfos: Array<Pair<String, Fragment>>
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return pagerInfos.size
    }

    override fun getItem(position: Int): Fragment {
        return pagerInfos[position].second
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pagerInfos[position].first
    }
}