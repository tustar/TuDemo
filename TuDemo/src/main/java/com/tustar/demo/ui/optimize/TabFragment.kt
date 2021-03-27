package com.tustar.demo.ui.optimize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentTabBinding
import com.tustar.demo.ex.bind
import dagger.hilt.android.AndroidEntryPoint

@DemoItem(
    group = R.string.group_optimize,
    item = R.string.optimize_lazy_fragment,
    createdAt = "2021-03-06 13:05:00",
    updatedAt = "2021-03-19 15:00:00",
)
@AndroidEntryPoint
class TabFragment : Fragment() {

    private val binding: FragmentTabBinding by bind()

    private val pagerInfos = arrayOf(
        "微信" to ChatsFragment.newInstance(),
        "联系人" to ContactsFragment.newInstance(),
        "发现" to DiscoverFragment.newInstance(),
        "我" to MeFragment.newInstance(),
    )
    private val pagerAdapter by lazy {
        TabPagerAdapter(parentFragmentManager, pagerInfos)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tabLayout.apply {
            setupWithViewPager(binding.viewPager, true)
            addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {

                }

                override fun onTabUnselected(tab: TabLayout.Tab) {

                }

                override fun onTabReselected(tab: TabLayout.Tab) {

                }
            })
        }
        binding.viewPager.apply {
            adapter = pagerAdapter
        }
    }
}