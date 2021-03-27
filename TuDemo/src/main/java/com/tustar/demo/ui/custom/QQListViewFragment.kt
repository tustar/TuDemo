package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentQqListViewBinding
import com.tustar.demo.databinding.ItemQqHeaderBinding
import com.tustar.demo.ex.bind
import com.tustar.demo.widget.QQListView
import dagger.hilt.android.AndroidEntryPoint

@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_qq_list_view,
    createdAt = "2021-02-27 12:00:00",
    updatedAt = "2021-03-18 16:00:00",
)
@AndroidEntryPoint
class QQListViewFragment : Fragment() {

    private val binding: FragmentQqListViewBinding by bind()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qq_list_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val header = ItemQqHeaderBinding.inflate(layoutInflater, null, false)
        qqListView.zoomView = header.qqHeaderBg
        qqListView.addHeaderView(header.root)
        qqListView.adapter = adapter
    }
}