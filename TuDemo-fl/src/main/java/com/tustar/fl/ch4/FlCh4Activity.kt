package com.tustar.fl.ch4

import android.os.Bundle
import com.tustar.fl.R
import com.tustar.fl.BaseBookActivity

class FlCh4Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.fl_ch4_title)
    }
}
