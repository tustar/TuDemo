package com.tustar.ryg.ch1

import android.os.Bundle
import com.tustar.ryg.R
import com.tustar.ryg.BaseBookActivity

class RygCh1Activity : BaseBookActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.ryg_ch1_title)
    }
}
