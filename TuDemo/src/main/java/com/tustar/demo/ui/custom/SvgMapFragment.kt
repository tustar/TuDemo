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
import com.tustar.demo.ex.bind
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

    private val viewModel: SvgMapViewModel by viewModels()
    private val binding: FragmentSvgMapBinding by bind()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_svg_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        viewModel.parserData(requireContext())
    }


    private fun subscribeUi() {
        with(viewModel) {
            provinces.observe(viewLifecycleOwner, Observer {
                binding.svgMapView.provinces = it
            })
        }
    }
}