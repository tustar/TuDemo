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

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var poiName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(
            inflater, container,
            false
        )
        subscribeUi()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun subscribeUi() {
        with(viewModel) {
            now.observe(viewLifecycleOwner, Observer {
                Logger.d("now = $it")
                it.now.updateView(requireContext(), binding)
                WeatherNotification.postNotification(requireContext(), it.now, poiName)
            })
        }

        (activity as MainActivity).liveLocation.observe(viewLifecycleOwner,
            Observer {
                poiName = it.poiName
                binding.address.text = it.poiName
                // Request weather
                viewModel.now(it)
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