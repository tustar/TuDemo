package com.tustar.demo.ui.ryg.ch1

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseBookActivity

class RygCh1Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.ryg_ch1_title)
    }
}
