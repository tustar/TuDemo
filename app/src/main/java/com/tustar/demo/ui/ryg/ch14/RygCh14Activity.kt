package com.tustar.demo.ui.ryg.ch14

import android.os.Bundle
import com.tustar.common.util.Logger
import com.tustar.demo.R
import com.tustar.demo.ui.ryg.base.BaseRygActivity
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
