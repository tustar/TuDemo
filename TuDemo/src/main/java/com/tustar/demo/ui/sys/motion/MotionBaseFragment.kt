package com.tustar.demo.ui.sys.motion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_SYSTEM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import com.tustar.demo.R

@RowGroup(id = GROUP_SYSTEM_WIDGET_ID, name = R.string.group_system_widget)
@RowDemo(
    groupId = GROUP_SYSTEM_WIDGET_ID, name = R.string.sys_motion_base,
    actionId = R.id.action_home_to_motion_base
)
class MotionBaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_motion_base, container, false)
    }
}