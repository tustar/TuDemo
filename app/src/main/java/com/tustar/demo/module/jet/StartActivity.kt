package com.tustar.demo.module.jet

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jet_start)
        title = getString(R.string.jet_start_title)
    }
}
