package com.tustar.ryg.ch14

import android.os.Bundle
import com.tustar.util.Logger
import com.tustar.ryg.R
import com.tustar.ryg.BaseBookActivity
import kotlinx.android.synthetic.main.activity_book_base.*

class RygCh14Activity : BaseBookActivity() {

    companion object {
        private val TAG = RygCh14Activity::class.java.simpleName
    }

    init {
        System.loadLibrary("tu-lib")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch14_title)

        book_base_empty_view.text = stringFromJNI()
    }

    external fun stringFromJNI(): String

    fun methodCalledByJni(msgFromJni: String) {
        Logger.d(TAG, "methodCalledByJni, msg: " + msgFromJni)
    }
}
