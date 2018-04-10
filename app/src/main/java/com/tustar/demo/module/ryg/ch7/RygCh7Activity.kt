package com.tustar.demo.module.ryg.ch7

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.module.ryg.base.BaseRygActivity
import com.tustar.common.util.Logger

class RygCh7Activity : BaseRygActivity() {

    companion object {
        private val TAG = RygCh7Activity::class.java.simpleName
    }

    init {
        sClassList.add(RygCh7ActivityAnimatorActivity::class.java)
        sClassList.add(RygCh7ScaleButtonActivity::class.java)
        sClassList.add(RygCh7LayoutAnimationActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        ryg_data_source = R.array.ryg_ch7_list
        super.onCreate(savedInstanceState)
        title = getString(R.string.ryg_ch7_title)
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
        if (clazz == RygCh7ActivityAnimatorActivity::class.java) {
            overridePendingTransition(R.anim.ryg_ch7_enter_anim, R.anim.ryg_ch7_exit_anim)
        }
    }
}
