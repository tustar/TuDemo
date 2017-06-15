package com.tustar.demo.module.ryg.ch2

import android.content.Intent
import android.os.Bundle

import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.util.Logger
import kotlinx.android.synthetic.main.activity_ryg_second.*

class RygSecondActivity : BaseActivity() {

    companion object {
        private val TAG = RygSecondActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_second)

        ryg_third_btn.setOnClickListener {
            startActivity(Intent(this@RygSecondActivity, RygThirdActivity::class.java))
        }

        Logger.d(TAG, "UserManager.sUserId = " + UserManager.sUserId)
    }
}
