package com.tustar.ryg.ch13

import android.os.Bundle
import com.tustar.ryg.R
import com.tustar.ryg.BaseBookActivity

class RygCh13Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch13_title)
    }
}
