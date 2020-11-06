package com.tustar.demo.ui.weather

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.TextView
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat
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
import com.tustar.demo.R

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private val viewModel by viewModels<WeatherViewModel>()

    private val hourlyAdapter by lazy {
        HourlyAdapter()
    }
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
            })
            hourly.observe(viewLifecycleOwner, Observer {
                hourlyAdapter.submitList(it.hourly)
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