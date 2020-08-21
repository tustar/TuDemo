package com.tustar.demo.ui.systemWidget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tustar.annotation.GROUP_SYSTEM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import com.tustar.demo.R

@RowGroup(id = GROUP_SYSTEM_WIDGET_ID, name = R.string.group_system_widget )
@RowDemo(groupId = GROUP_SYSTEM_WIDGET_ID, name = R.string.custom_motion_layout,
    actionId = R.id.action_home_to_motion_layout)
class MotionLayoutFragment : Fragment() {

    private val viewModel: MotionLayoutViewModel by viewModels {
        MotionLayoutViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_motion_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance() = MotionLayoutFragment()
    }
}