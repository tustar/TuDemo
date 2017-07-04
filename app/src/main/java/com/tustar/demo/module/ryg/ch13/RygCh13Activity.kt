package com.tustar.demo.module.ryg.ch13

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh13Activity : BaseActivity    () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch13)
        title = getString(R.string.ryg_ch13_title)
    }
}
