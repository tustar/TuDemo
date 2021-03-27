package com.tustar.demo.ui.weather

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.work.*
import com.tustar.demo.databinding.FragmentWeatherBinding
import com.tustar.demo.ex.bind
import com.tustar.demo.ui.KEY_LOCATION
import com.tustar.demo.ui.MainActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.R
import com.tustar.demo.ui.MainActivity3
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel:WeatherViewModel by viewModels()
    private val binding: FragmentWeatherBinding by bind()

    private val hourlyAdapter by lazy {
        HourlyAdapter()
    }
    private lateinit var poiName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.temp.setOnClickListener {
        }
        binding.temp.setOnLongClickListener {
            it.announceForAccessibility("Edit Mode")
            it.sendAccessibilityEvent(AccessibilityEvent.TYPE_ANNOUNCEMENT)
            return@setOnLongClickListener true
        }

        //        ViewCompat.setAccessibilityDelegate(binding.temp, object : AccessibilityDelegateCompat() {
//            override fun onPopulateAccessibilityEvent(host: View, event: AccessibilityEvent) {
//                super.onPopulateAccessibilityEvent(host, event)
//            }
//
//            override fun onInitializeAccessibilityNodeInfo(
//                host: View,
//                info: AccessibilityNodeInfoCompat
//            ) {
//                super.onInitializeAccessibilityNodeInfo(host, info)
//                info.addAction(
//                    AccessibilityActionCompat(
//                        AccessibilityNodeInfoCompat.ACTION_CLICK, "ACTION_CLICK"
//                    )
//                )
//                info.addAction(
//                    AccessibilityActionCompat(
//                        AccessibilityNodeInfoCompat.ACTION_LONG_CLICK, "ACTION_LONG_CLICK"
//                    )
//                )
//            }
//        })
        binding.hourlyRecyclerView.adapter = hourlyAdapter
        subscribeUi()
    }

    private fun subscribeUi() {
        with(viewModel) {
            now.observe(viewLifecycleOwner, Observer {
                Logger.d("now = $it")
                it.now.updateView(requireContext(), binding)
            })
            hourly.observe(viewLifecycleOwner, Observer {
                hourlyAdapter.submitList(it.hourly)
            })
        }

        (activity as MainActivity3).liveLocation.observe(viewLifecycleOwner,
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