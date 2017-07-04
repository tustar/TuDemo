package com.tustar.demo.module.ryg.ch11

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh11Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch11)
        title = getString(R.string.ryg_ch11_title)
    }
}
