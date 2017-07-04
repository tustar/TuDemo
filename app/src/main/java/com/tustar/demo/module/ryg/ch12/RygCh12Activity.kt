package com.tustar.demo.module.ryg.ch12

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh12Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch12)
        title = getString(R.string.ryg_ch12_title)
    }
}
