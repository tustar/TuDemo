package com.tustar.demo.module.ryg.ch5

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh5NotificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch5_notification)
        title = getString(R.string.ryg_ch5_notification)
    }
}
