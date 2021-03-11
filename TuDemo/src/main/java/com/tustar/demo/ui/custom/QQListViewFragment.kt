package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tustar.annotation.GROUP_CUSTOM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentQqListViewBinding
import com.tustar.demo.widget.QQListView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_qq_header.view.*

@RowDemo(
    groupId = GROUP_CUSTOM_WIDGET_ID, name = R.string.custom_qq_list_view,
    actionId = R.id.action_home_to_qq_list_view
)
@AndroidEntryPoint
class QQListViewFragment : Fragment() {

    companion object {
        fun newInstance() = QQListViewFragment()
    }

    private val viewModel by viewModels<QQListViewViewModel>()
    private var _binding: FragmentQqListViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQqListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView(binding.qqListView)
    }

    private fun initView(qqListView: QQListView) {
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_list_item_1,
            arrayOf(
                "星期一 	和马云洽谈",
                "星期二	约见李彦宏",
                "星期三 	约见乔布斯",
                "星期四 	和Lance钓鱼",
                "星期五 	和Jett洽谈",
                "星期六 	和Jason洽谈",
                "星期日 	和MZ洽谈",
                "星期一 	和马云洽谈",
                "星期二	约见李彦宏",
                "星期三 	约见乔布斯",
                "星期四 	和Ricky钓鱼",
                "星期五 	和David洽谈",
                "星期六 	和Jason洽谈",
                "星期日 	和MZ洽谈",
                "……"
            )
        )
        val header = View.inflate(requireContext(), R.layout.item_qq_header, null)
        qqListView.zoomView = header.rootView.qq_header_bg
        qqListView.addHeaderView(header)
        qqListView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}