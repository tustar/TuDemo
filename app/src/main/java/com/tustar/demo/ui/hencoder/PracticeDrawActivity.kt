package com.tustar.demo.ui.hencoder


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import java.util.*

abstract class PracticeDrawActivity : BaseActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager: ViewPager
    internal var pageModels: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hencoder_main)

        pager = findViewById(R.id.pager)
        pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getCount(): Int = pageModels.size

            override fun getItem(position: Int): Fragment = createFragment(position)

            override fun getPageTitle(position: Int): CharSequence =
                    pageModels[position]
        }

        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(pager)
    }

    abstract fun createFragment(position: Int): Fragment
}
