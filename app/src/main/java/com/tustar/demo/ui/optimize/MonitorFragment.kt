package com.tustar.demo.ui.optimize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tustar.annotation.DemoItem
import com.tustar.demo.R
import com.tustar.demo.databinding.FragmentMonitorBinding
import com.tustar.demo.ex.bind
import com.tustar.demo.util.Logger
import dagger.hilt.android.AndroidEntryPoint

@DemoItem(
    group = R.string.group_optimize,
    item = R.string.optimize_monitor,
    createdAt = "2021-03-13 16:00:00",
    updatedAt = "2021-03-21 07:00:00",
)
@AndroidEntryPoint
class MonitorFragment : Fragment() {

    private val binding: FragmentMonitorBinding by bind()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_monitor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.monitorCreate.setOnClickListener {
            create()
        }
        binding.monitorGc.setOnClickListener {
            gc()
        }
    }

    override fun onResume() {
        Logger.i()
        super.onResume()
    }

    override fun onPause() {
        Logger.i()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Monitor.release()
    }

    private fun create() {
        Thread {
            for (i in 0..999) {
                val javaBean = JavaBean()
            }
        }.start()
    }

    private fun gc() {
        try {
            System.gc()
            System.runFinalization()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class JavaBean {
        var bytes = ByteArray(2048)
    }
}