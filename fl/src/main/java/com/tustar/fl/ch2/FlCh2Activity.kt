package com.tustar.fl.ch2

import android.os.Bundle
import com.tustar.fl.BaseBookActivity
import com.tustar.fl.R

class FlCh2Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.fl_ch2_title)
    }
}
