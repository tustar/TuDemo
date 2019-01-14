package com.tustar.demo.ui.fl.ch4

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseBookActivity

class FlCh4Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.fl_ch4_title)
    }
}
