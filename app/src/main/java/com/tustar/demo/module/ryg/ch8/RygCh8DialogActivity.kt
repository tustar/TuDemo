package com.tustar.demo.module.ryg.ch8

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RygCh8DialogActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch8_dialog)
        title = getString(R.string.ryg_ch8_dialog)

        initView()
    }

    private fun initView() {
        val dialog = Dialog(this)
        val textView = TextView(this)
        textView.text = "This is toast!"
        dialog.setContentView(textView)
        dialog.window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR)
        dialog.show()
    }
}
