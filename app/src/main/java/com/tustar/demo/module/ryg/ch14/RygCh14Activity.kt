package com.tustar.demo.module.ryg.ch14

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch5.BaseRygActivity

class RygCh14Activity : BaseRygActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch14_title)
    }
}
