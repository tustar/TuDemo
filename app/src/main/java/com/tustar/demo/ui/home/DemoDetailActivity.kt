package com.tustar.demo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tustar.demo.R
import com.tustar.demo.ui.optimize.MonitorFragment
import com.tustar.demo.util.Logger
import dagger.hilt.android.AndroidEntryPoint


const val INTENT_KEY_DEMO_ID = "demoId"

@AndroidEntryPoint
class DemoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("$savedInstanceState")
        setContentView(R.layout.demo_detail_activity)

        val demoId = intent.getIntExtra(INTENT_KEY_DEMO_ID, -1)
        val fragment = demoId.toFragment()
        if (fragment == null) {
            finish()
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment!!)
                .commitNow()
        }
    }

    private fun Int.toFragment(): Fragment? = when (this) {
        R.string.optimize_monitor -> MonitorFragment.newInstance()
        else -> null
    }
}
