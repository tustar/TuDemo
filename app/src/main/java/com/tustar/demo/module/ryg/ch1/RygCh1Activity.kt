package com.tustar.demo.module.ryg.ch1

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh1Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch1)

        title = getString(R.string.ryg_ch1_title)
    }
}
