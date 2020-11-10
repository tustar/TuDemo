package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_CUSTOM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentCustomFlowLayoutBinding
import com.tustar.demo.databinding.ItemFlowBinding
import com.tustar.demo.widget.FlowLayout
import com.tustar.demo.widget.FlowLayout.Adapter

@RowDemo(
    groupId = GROUP_CUSTOM_WIDGET_ID, name = R.string.custom_flow_layout,
    actionId = R.id.action_home_to_flow_layout
)
class CustomFlowLayoutFragment : Fragment() {

    private lateinit var binding: FragmentCustomFlowLayoutBinding
    private val flowAdapter: FlowAdapter by lazy {
        FlowAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomFlowLayoutBinding.inflate(
            inflater, container,
            false
        )
        with(binding) {
            flowHistory.setAdapter(flowAdapter)
            flowDiscover.setAdapter(flowAdapter)
        }
        //
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance() = CustomFlowLayoutFragment()
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