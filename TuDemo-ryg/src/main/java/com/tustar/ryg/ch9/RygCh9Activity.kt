package com.tustar.ryg.ch9

import android.os.Bundle
import com.tustar.ryg.R
import com.tustar.ryg.BaseBookActivity

class RygCh9Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch9_title)
    }
}
