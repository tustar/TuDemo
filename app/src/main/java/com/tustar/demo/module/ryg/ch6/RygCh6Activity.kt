package com.tustar.demo.module.ryg.ch6

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch5.BaseRygActivity

class RygCh6Activity : BaseRygActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch6_title)
    }
}
