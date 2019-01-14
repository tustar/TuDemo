package com.tustar.demo.ui.ryg.ch7

import android.animation.IntEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import com.tustar.common.util.Logger
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ryg_ch7_scale_button.*


class RygCh7ScaleButtonActivity : BaseActivity() {

    companion object {
        private val TAG = RygCh7ScaleButtonActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch7_scale_button)
        title = getString(R.string.ryg_ch7_scale_button)

        ryg_ch7_scale_btn.setOnClickListener {
            performAnimatorWidth(ryg_ch7_scale_btn, ryg_ch7_scale_btn.height, 500)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            performAnimator(ryg_ch7_scale_btn, ryg_ch7_scale_btn.width, 500)
        }
    }

    private fun performAnimator(target: View, start: Int, end: Int) {
        val valueAnimator = ValueAnimator.ofInt(1, 100)
        valueAnimator.addUpdateListener { animation ->
            val currentValue = animation.animatedValue
            Logger.d(TAG, "onAnimationUpdate :: currentValue: $currentValue")
            val fraction = animation.animatedFraction
            target.layoutParams.width = IntEvaluator().evaluate(fraction, start, end)
            target.requestLayout()
        }
        valueAnimator.setDuration(5000).start()
    }

    private fun performAnimatorWidth(target: View, start: Int, end: Int) {
        Logger.d(TAG, "performAnimatorWidth")
        val wrapper = ViewWrapper(target)
        ObjectAnimator.ofInt(wrapper, "height", start, end).setDuration(5000).start()
    }
}

class ViewWrapper(var target: View) {

    fun setHeight(value: Int) {
        target.layoutParams.height = value
        target.requestLayout()
    }

    fun getHeight() = target.layoutParams.height

}
