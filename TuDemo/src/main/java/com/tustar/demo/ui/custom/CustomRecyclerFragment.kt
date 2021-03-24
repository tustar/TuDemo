package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.GROUP_CUSTOM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.annotation.RowGroup
import com.tustar.demo.R
import dagger.hilt.android.AndroidEntryPoint

@RowGroup(id = GROUP_CUSTOM_WIDGET_ID, name = R.string.group_custom_widget)
@RowDemo(
    groupId = GROUP_CUSTOM_WIDGET_ID, name = R.string.custom_recycler_view,
    actionId = R.id.action_home_to_recycler
)
@AndroidEntryPoint
class CustomRecyclerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_recycler, container, false)
    }
}