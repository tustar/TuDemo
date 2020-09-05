package com.tustar.demo.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tustar.demo.databinding.FragmentWeatherBinding

class WeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(requireContext())
    }

    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentWeatherBinding.inflate(
            inflater, container,
            false
        )
        weatherAdapter = WeatherAdapter()
        binding.weatherRecyclerView.apply {
            adapter = weatherAdapter
            val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
        }
        subscribeUi()
        viewModel.searchPlaces("北京")
        return binding.root
    }

    private fun subscribeUi() {
        with(viewModel) {
            places.observe(viewLifecycleOwner, Observer { items ->
                weatherAdapter.submitList(items)
            })
        }
    }
}