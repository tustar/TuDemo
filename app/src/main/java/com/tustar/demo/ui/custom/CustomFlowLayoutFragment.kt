package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentCustomFlowLayoutBinding
import com.tustar.demo.databinding.ItemFlowBinding
import com.tustar.demo.ex.bind
import com.tustar.demo.widget.FlowLayout
import com.tustar.demo.widget.FlowLayout.Adapter
import dagger.hilt.android.AndroidEntryPoint

@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_flow_layout,
    createdAt = "2020-10-15 12:20:50",
    updatedAt = "2021-03-15 15:30:10",
)
@AndroidEntryPoint
class CustomFlowLayoutFragment : Fragment() {

    private val binding: FragmentCustomFlowLayoutBinding by bind()

    private val flowAdapter: FlowAdapter by lazy {
        FlowAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_flow_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            flowHistory.setAdapter(flowAdapter)
            flowDiscover.setAdapter(flowAdapter)
        }
    }

    class FlowAdapter : Adapter<FlowLayout.ViewHolder>() {

        private val words = arrayListOf(
            "进口奶粉", "婴儿奶粉", "全脂奶粉", "澳洲奶粉",
            "新西兰奶粉", "脱脂奶粉", "成人奶粉", "德国奶粉",
            "俄罗斯奶粉"
        )

        override fun getItemCount(): Int {
            return words.size
        }

        override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
            val binding = ItemFlowBinding.inflate(LayoutInflater.from(parent.context))
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: FlowLayout.ViewHolder, position: Int) {
            if (holder is ViewHolder) {
                holder.bind(words[position])
            }
        }

        class ViewHolder(val binding: ItemFlowBinding) : FlowLayout.ViewHolder(binding.root) {

            fun bind(word: String) {
                binding.keyword.text = word
            }
        }
    }
}