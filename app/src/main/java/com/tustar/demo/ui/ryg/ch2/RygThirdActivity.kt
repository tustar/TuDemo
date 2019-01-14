package com.tustar.demo.ui.ryg.ch2

import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygThirdActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.tustar.demo.R.layout.activity_ryg_third)
        title = getString(R.string.ryg_ch2_third)
    }
}
