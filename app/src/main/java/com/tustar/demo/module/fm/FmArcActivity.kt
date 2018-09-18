package com.tustar.demo.module.fm

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_fm_arc.*

class FmArcActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_arc)
        title = getString(R.string.fm_arc)

        play_btn.setOnClickListener{
            display_arc.startAnimation()
        }
        reverse_btn.setOnClickListener{
            display_arc.startAnimation(true)
        }
    }
}
