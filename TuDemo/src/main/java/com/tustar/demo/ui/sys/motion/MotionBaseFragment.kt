package com.tustar.demo.ui.sys.motion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.DemoItem
import com.tustar.demo.R

@DemoItem(
    group = R.string.group_system_widget,
    item = R.string.sys_motion_base,
    createdAt = "2020-07-15 12:10:00",
    updatedAt = "2021-02-18 15:10:00",
)
class MotionBaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_motion_base, container, false)
    }
}