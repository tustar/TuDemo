package com.tustar.demo.ui.ryg.ch1

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.ui.ryg.base.BaseRygActivity

class RygCh1Activity : BaseRygActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.ryg_ch1_title)
    }
}
