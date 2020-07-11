package com.tustar.fl.ch1

import android.os.Bundle
import com.tustar.fl.R
import com.tustar.fl.BaseBookActivity

class FlCh1Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.fl_ch1_title)
    }
}
