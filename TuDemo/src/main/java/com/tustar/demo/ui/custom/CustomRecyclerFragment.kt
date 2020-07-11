package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tustar.annotation.GROUP_CUSTOM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import com.tustar.demo.R

@RowGroup(id = GROUP_CUSTOM_WIDGET_ID, name = R.string.group_custom_widget )
@RowDemo(groupId = GROUP_CUSTOM_WIDGET_ID, name = R.string.custom_recycler_view,
    actionId = R.id.action_main_to_recycler)
class CustomRecyclerFragment : Fragment() {
    private val viewModel: CustomRecyclerViewModel by viewModels {
        CustomRecyclerViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_recycler, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance() = CustomRecyclerFragment()
    }
}