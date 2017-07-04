package com.tustar.demo.module.ryg.ch10

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh10Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch10)
        title = getString(R.string.ryg_ch10_title)
    }
}
