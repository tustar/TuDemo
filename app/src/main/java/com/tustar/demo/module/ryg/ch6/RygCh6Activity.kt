package com.tustar.demo.module.ryg.ch6

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.module.ryg.ch5.BaseRygActivity

class RygCh6Activity : BaseRygActivity() {

    init {
        sClassList.add(RygCh6DrawableActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_data_source = R.array.ryg_ch6_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch6_title)
    }
}
