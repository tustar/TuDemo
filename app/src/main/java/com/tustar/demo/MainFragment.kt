package com.tustar.demo

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tustar.demo.databinding.FragmentMainBinding
import com.tustar.sample.ui.SamplesActivity
import com.tustar.ui.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override val bindingInflate: (LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun initViews() {
        with(binding) {
            mainSamples.setOnClickListener {
                val intent = Intent().apply {
                    setClass(requireContext(), SamplesActivity::class.java)
                }
                startActivity(intent)
            }
            mainWeather.setOnClickListener {

            }
        }
    }

    override fun initData() {
    }
}