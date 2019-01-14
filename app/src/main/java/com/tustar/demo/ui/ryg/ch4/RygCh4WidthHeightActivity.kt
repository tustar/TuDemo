package com.tustar.demo.ui.ryg.ch4

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View.MeasureSpec
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ryg_ch4_width_height.*


class RygCh4WidthHeightActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch4_width_height)
        title = getString(R.string.ryg_ch4_width_height)

        ryg_ch4_wh_btn.setOnClickListener {
            generateText("OnClick")
        }
        ryg_ch4_wh_text.movementMethod = ScrollingMovementMethod()

        measureView()
    }


    override fun onStart() {
        super.onStart()
        ryg_ch4_wh_btn.post {
            generateText("view.post")
        }

        ryg_ch4_wh_btn.viewTreeObserver.addOnGlobalLayoutListener {
            ryg_ch4_wh_btn.viewTreeObserver.removeOnGlobalLayoutListener {

            }
            generateText("viewTreeObserver.onGlobalLayout")
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            generateText("onWindowFocusChanged")
        }
    }

    private fun generateText(method: String) {
        var sb = StringBuilder(ryg_ch4_wh_text.text)
        sb.append("---------------------------------\n")
        sb.append("$method\n")
        sb.append("measuredWidth = ${ryg_ch4_wh_btn.measuredWidth}, measuredHeight = " +
                "${ryg_ch4_wh_btn.measuredHeight}\n")
        sb.append("width = ${ryg_ch4_wh_btn.width}, height = ${ryg_ch4_wh_btn.height}\n")
        ryg_ch4_wh_text.text = sb.toString()
    }

    private fun measureView() {
        val widthMeasureSpec = MeasureSpec.makeMeasureSpec((1 shl 30) - 1, MeasureSpec.AT_MOST)
        val heightMeasureSpec = MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY)
        ryg_ch4_wh_text.measure(widthMeasureSpec, heightMeasureSpec)
        generateText("measureView")
    }

}

