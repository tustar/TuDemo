package com.tustar.demo.ui.weather

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.work.*
import com.tustar.demo.databinding.FragmentWeatherBinding
import com.tustar.demo.ui.KEY_LOCATION
import com.tustar.demo.ui.MainActivity
import com.tustar.demo.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel by viewModels<WeatherViewModel>()

    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(
            inflater, container,
            false
        )
        weatherAdapter = WeatherAdapter()
        subscribeUi()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun subscribeUi() {
        with(viewModel) {
            places.observe(viewLifecycleOwner, Observer { items ->
                weatherAdapter.submitList(items)
            })
            realtime.observe(viewLifecycleOwner, Observer {
                Logger.d("realtime = $it")
                it.updateView(requireContext(), binding)
            })
        }

        (activity as MainActivity).liveLocation.observe(viewLifecycleOwner,
            Observer {
                binding.address.text = it.poiName
                // Request weather
                viewModel.getRealtime(it)
                //
//                startWorker(it)
            })
    }

    private fun startWorker(location: Location) {
        val data = workDataOf(KEY_LOCATION to location)
        val weatherWorkRequest = PeriodicWorkRequestBuilder<WeatherWorker>(1, TimeUnit.MINUTES)
            .setInputData(data)
            .build()
        val workerManager = WorkManager.getInstance(requireContext())
        workerManager.enqueueUniquePeriodicWork(
            "getWeather",
            ExistingPeriodicWorkPolicy.KEEP,
            weatherWorkRequest
        )
        workerManager.getWorkInfoByIdLiveData(weatherWorkRequest.id)
            .observe(viewLifecycleOwner, Observer { workInfo ->
                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {

                }
            })
    }
}