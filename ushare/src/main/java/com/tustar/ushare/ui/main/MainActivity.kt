package com.tustar.ushare.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.tustar.ushare.R
import com.tustar.ushare.base.BaseActivity
import com.tustar.ushare.ui.login.LoginActivity
import com.tustar.ushare.ui.lot.LotFragment
import com.tustar.ushare.ui.mine.MineFragment
import com.tustar.ushare.ui.topic.TopicFragment
import org.jetbrains.anko.find


class MainActivity : BaseActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: TabPagerAdapter
    private var tabItems = mutableMapOf<Int, TabItem>()
    private val listener = View.OnClickListener {
        val tab = tabLayout.getTabAt(it.tag as Int)
        tab?.let {
            if (!it.isSelected) {
                it.select()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ushare_activity_main)
        setActionBar()
        setDarkStatusIcon(false)
        setStatusBarColor(R.color.action_bar_bg_color)

        initViews()
    }

    override fun setActionBar() {
        super.setActionBar()
        actionBarBack?.visibility = View.GONE
    }

    override fun initViews() {
        super.initViews()
        setTitle(R.string.ushare_app_name)

        initViewPager()

        initTabLayout()
    }

    private fun initViewPager() {
        viewPager = find(R.id.view_pager)
        adapter = TabPagerAdapter(supportFragmentManager)
        adapter.addFragment(LotFragment.newInstance())
        adapter.addFragment(TopicFragment.newInstance())
        adapter.addFragment(MineFragment.newInstance())
        viewPager.adapter = adapter
    }

    private fun initTabLayout() {
        tabLayout = find(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager, true)

        for (i in 0..tabLayout.tabCount) {
            var tab = tabLayout.getTabAt(i)
            tab?.customView = getTabItemView(i)
            tab?.customView?.let {
                val tabView = it.parent as View
                tabView.tag = i
                tabView.setOnClickListener(listener)
            }
        }
    }

    override fun onBackPressed() {
        forcedExitOnBackPressed()
    }

    private fun toLoginUI() {
        val intent = Intent(this, LoginActivity::class.java).apply {

        }
        startActivity(intent)
    }

    private fun getTabItemView(position: Int): View {
        val itemView = layoutInflater.inflate(R.layout.ushare_item_tab, null)
        tabItems[position] = TabItem(itemView, position)
        itemView.tag = position
        itemView.setOnClickListener(listener)
        return itemView
    }

    inner class TabItem(itemView: View, position: Int) {

        lateinit var icon: ImageView
        lateinit var text: TextView

        init {
            with(itemView) {
                icon = find(R.id.tab_bar_item_image)
                text = find(R.id.tab_bar_item_text)
            }

            when (position) {
                TAB_LOT -> {
                    icon.setImageResource(R.drawable.selector_ic_lot)
                    text.text = getString(R.string.tab_lot)
                }
                TAB_TOPIC -> {
                    icon.setImageResource(R.drawable.selector_ic_topic)
                    text.text = getString(R.string.tab_topic)
                }
                TAB_MINE -> {
                    icon.setImageResource(R.drawable.selector_ic_mine)
                    text.text = getString(R.string.tab_mine)
                }
            }
        }
    }

    companion object {
        const val TAB_LOT = 0
        const val TAB_TOPIC = 1
        const val TAB_MINE = 2
    }
}
