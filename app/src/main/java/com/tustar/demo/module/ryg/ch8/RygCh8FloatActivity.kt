package com.tustar.demo.module.ryg.ch8

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import android.view.WindowManager.LayoutParams
import android.widget.Button
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.common.util.Logger
import kotlinx.android.synthetic.main.activity_ryg_ch8_float.*


class RygCh8FloatActivity : BaseActivity() {

    companion object {
        private val TAG = RygCh8FloatActivity::class.java.simpleName
        private val REQUEST_ALERT_WINDOW = 0x1
    }

    private var mFloatingButton: Button? = null
    private lateinit var mLayoutParams: WindowManager.LayoutParams
    private lateinit var mWindowManager: WindowManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch8_float)
        title = getString(R.string.ryg_ch8_float_button)

        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        requestAlertWindowPermissions()
    }

    private fun initBtn() {
        ryg_ch8_btn.setOnClickListener {
            if (mFloatingButton != null) {
                return@setOnClickListener
            }

            mFloatingButton = Button(RygCh8FloatActivity@ this)
            mFloatingButton!!.text = "click me"
            mLayoutParams = LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0, 0,
                    PixelFormat.TRANSPARENT)
            mLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL or LayoutParams.FLAG_NOT_FOCUSABLE or LayoutParams.FLAG_SHOW_WHEN_LOCKED
            mLayoutParams.type = LayoutParams.TYPE_SYSTEM_ERROR
            mLayoutParams.gravity = Gravity.LEFT or Gravity.TOP
            mLayoutParams.x = 100
            mLayoutParams.y = 300
            mFloatingButton!!.setOnTouchListener { _, event ->
                val rawX = event.rawX.toInt()
                val rawY = event.rawY.toInt()
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        mLayoutParams.x = rawX
                        mLayoutParams.y = rawY
                        mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams)
                    }
                    else -> {

                    }
                }
                return@setOnTouchListener false
            }
            mWindowManager.addView(mFloatingButton, mLayoutParams)
        }
    }

    override fun onDestroy() {
        if (mWindowManager != null && mFloatingButton != null) {
            mWindowManager.removeView(mFloatingButton)
        }
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_ALERT_WINDOW -> {
                if (hasAlertWindowPermissions()) {
                    initBtn()
                } else {
                    Logger.w(TAG, "onActivityResult :: No Permission")
                }
            }
        }
    }

    private fun hasAlertWindowPermissions(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        if (Settings.canDrawOverlays(this)) {
            return true
        }

        return false
    }

    private fun requestAlertWindowPermissions() {
        if (hasAlertWindowPermissions()) {
            initBtn()
        } else {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, REQUEST_ALERT_WINDOW)
        }
    }
}
