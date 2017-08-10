package com.tustar.demo.module.ryg.ch7

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch5.BaseRygActivity

class RygCh7Activity : BaseRygActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch7_title)
    }
}
