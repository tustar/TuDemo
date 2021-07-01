package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import dagger.hilt.android.AndroidEntryPoint

@DemoItem(
    group = R.string.group_custom_widget,
    item = R.string.custom_view,
    createdAt = "2020-12-24 20:00:00",
    updatedAt = "2021-03-15 15:00:00",
)
@AndroidEntryPoint
class CustomViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_view, container, false)
    }
}