package com.tustar.fl.ch3

import android.os.Bundle
import com.tustar.fl.BaseBookActivity
import com.tustar.fl.R

class FlCh3Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.fl_ch3_title)
    }
}
