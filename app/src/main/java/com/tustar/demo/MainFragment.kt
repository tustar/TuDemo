package com.tustar.demo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.tustar.annotation.Sample
import com.tustar.ui.BaseFragment
import com.tustar.sample.ui.SamplesActivity
import com.tustar.weather.ui.WeatherActivity

@Sample(
    group = "group_custom_widget",
    item = "custom_composes_example",
    createdAt = "2022-09-25 10:15:00",
    updatedAt = "2021-09-26 15:24:00",
)
class MainFragment : BaseFragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        with(view) {
            val mainSamples = findViewById<TextView>(R.id.main_samples)
            mainSamples.setOnClickListener {
                val intent = Intent().apply {
                    setClass(requireContext(), SamplesActivity::class.java)
                }
                startActivity(intent)
            }
            val mainWeather = findViewById<TextView>(R.id.main_weather)
            mainWeather.setOnClickListener {
                val intent = Intent().apply {
                    setClass(requireContext(), WeatherActivity::class.java)
                }
                startActivity(intent)
            }
        }
    }
}