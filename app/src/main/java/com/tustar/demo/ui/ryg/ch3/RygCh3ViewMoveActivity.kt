package com.tustar.demo.ui.ryg.ch3

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ryg_ch3_view_move.*

class RygCh3ViewMoveActivity : BaseActivity() {

    companion object {
        private val TAG = RygCh3ViewMoveActivity::class.java.simpleName
        private val MESSAGE_SCROLL_TO = 1
        private val FRAME_COUNT = 30
        private val DELAYED_TIME = 33L
    }

    private var mCount = 0
    private var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_SCROLL_TO -> {
                    mCount++
                    if (mCount <= FRAME_COUNT) {
                        var fraction = mCount / FRAME_COUNT.toFloat()
                        var scrollX = fraction * 100
                        ryg_ch3_handler_move.scrollTo(scrollX.toInt(), 0)
                        sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch3_view_move)
        title = getString(R.string.ryg_ch3_view_move)

        ryg_ch3_object_animator.setOnClickListener {
            ObjectAnimator.ofFloat(it, View.TRANSLATION_X, 0f, 100f)
                    .setDuration(100)
                    .start()
        }

        ryg_ch3_layout_params.setOnClickListener {
            var params = it.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin += 10
            params.leftMargin += 10
//            it.layoutParams = params
            //
            it.requestLayout()
        }

        ryg_ch3_handler_move.setOnClickListener {
            mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO, DELAYED_TIME)
        }
    }
}
