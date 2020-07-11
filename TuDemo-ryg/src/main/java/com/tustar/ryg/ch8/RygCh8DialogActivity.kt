package com.tustar.ryg.ch8

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import com.tustar.ryg.R
import androidx.appcompat.app.AppCompatActivity

class RygCh8DialogActivity : AppCompatActivity() {

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
        dialog.window!!.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR)
        dialog.show()
    }
}
