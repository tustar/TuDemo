package com.tustar.demo.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tustar.annotation.GROUP_CUSTOM_WIDGET_ID
import com.tustar.annotation.RowDemo
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentSvgMapBinding
import com.tustar.demo.databinding.FragmentTodoBinding
import com.tustar.demo.ui.weather.updateView
import com.tustar.demo.util.Logger
import dagger.hilt.android.AndroidEntryPoint

@RowDemo(
    groupId = GROUP_CUSTOM_WIDGET_ID, name = R.string.custom_svg_china,
    actionId = R.id.action_home_to_svg_china
)
@AndroidEntryPoint
class SvgMapFragment : Fragment() {

    companion object {
        fun newInstance() = SvgMapFragment()
    }

    private val viewModel by viewModels<SvgMapViewModel>()

    private var _binding: FragmentSvgMapBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSvgMapBinding.inflate(
            inflater, container,
            false
        )
        subscribeUi()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.parserData(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeUi() {
        with(viewModel) {
            provinces.observe(viewLifecycleOwner, Observer {
                binding.svgMapView.provinces = it
            })
        }
    }
}