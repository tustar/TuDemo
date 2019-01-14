package com.tustar.demo.ui.ryg.ch7

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh7ActivityAnimatorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch7_activity_animator)
        title = getString(R.string.ryg_ch7_activity_animator)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.ryg_ch7_enter_anim, R.anim.ryg_ch7_exit_anim)
    }
}
