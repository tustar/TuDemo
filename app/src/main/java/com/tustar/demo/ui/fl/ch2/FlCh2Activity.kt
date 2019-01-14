package com.tustar.demo.ui.fl.ch2

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseBookActivity

class FlCh2Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.fl_ch2_title)
    }
}
