package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tustar.annotation.GROUP_CUSTOM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentPathMeasureBinding
import dagger.hilt.android.AndroidEntryPoint

@RowDemo(
    groupId = GROUP_CUSTOM_WIDGET_ID, name = R.string.custom_path_measure,
    actionId = R.id.action_home_to_path_measure
)
@AndroidEntryPoint
class PathMeasureFragment : Fragment() {

    companion object {
        fun newInstance() = PathMeasureFragment()
    }

    private val viewModel = viewModels<PathMeasureViewModel>()

    private var _binding: FragmentPathMeasureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPathMeasureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}