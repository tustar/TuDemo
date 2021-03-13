package com.tustar.demo.ui.optimize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tustar.annotation.GROUP_OPTIMIZE
import com.tustar.annotation.RowDemo
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentTabBinding
import dagger.hilt.android.AndroidEntryPoint

@RowDemo(
    groupId = GROUP_OPTIMIZE, name = R.string.optimize_lazy_fragment,
    actionId = R.id.action_home_to_lazy_fragment
)
@AndroidEntryPoint
class TabFragment : Fragment() {

    private val viewModel by viewModels<TabViewModel>()

    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding!!

    private val fragments = arrayOf(
        ChatsFragment.newInstance(),
        ContactsFragment.newInstance(),
        DiscoverFragment.newInstance(),
        MeFragment.newInstance(),
    )
    private val pagerAdapter by lazy {
        TabPagerAdapter(parentFragmentManager, fragments)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initView() {
        binding.viewPager.apply {
            adapter = pagerAdapter
        }
        binding.tabLayout.apply {
            setupWithViewPager(binding.viewPager, true)
        }
    }

    companion object {
        fun newInstance() = TabFragment()
    }
}