package com.tustar.demo.ui.sys.motion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_SYSTEM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.demo.R

@RowDemo(
    groupId = GROUP_SYSTEM_WIDGET_ID, name = R.string.sys_motion_image_filter_view,
    actionId = R.id.action_home_to_motion_image_filter_view
)
class MotionImageFilterViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_motion_image_filter_view, container, false)
    }
}