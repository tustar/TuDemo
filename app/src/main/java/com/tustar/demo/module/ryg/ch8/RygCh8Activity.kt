package com.tustar.demo.module.ryg.ch8

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.module.ryg.base.BaseRygActivity

class RygCh8Activity : BaseRygActivity() {


    init {
        sClassList.add(RygCh8FloatActivity::class.java)
        sClassList.add(RygCh8DialogActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_data_source = R.array.ryg_ch8_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch8_title)
    }
}
