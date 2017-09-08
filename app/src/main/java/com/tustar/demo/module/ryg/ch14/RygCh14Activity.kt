package com.tustar.demo.module.ryg.ch14

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.module.ryg.base.BaseRygActivity
import com.tustar.demo.util.Logger
import kotlinx.android.synthetic.main.activity_ryg_base.*

class RygCh14Activity : BaseRygActivity() {

    companion object {
        private val TAG = RygCh14Activity::class.java.simpleName
    }

    init {
        System.loadLibrary("tu-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch14_title)

        rgy_base_empty_view.text = stringFromJNI()
    }

    external fun stringFromJNI(): String

    fun methodCalledByJni(msgFromJni: String) {
        Logger.d(TAG, "methodCalledByJni, msg: " + msgFromJni)
    }
}
