package com.tustar.ushare.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageButton
import android.widget.TextView
import com.tustar.common.util.ActivityCollector
import com.tustar.common.util.Logger
import com.tustar.ushare.R
import org.jetbrains.anko.toast


open class BaseActivity : AppCompatActivity() {

    open var actionBarBack: ImageButton? = null
    open var actionBarTitle: TextView? = null
    private var firstPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    open fun setActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.action_bar)
        actionBarTitle = findViewById(R.id.action_bar_title)
        actionBarBack = findViewById(R.id.action_bar_back)
        actionBarBack?.setOnClickListener {
            finish()
        }

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        actionBarTitle?.text = title
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setFullStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
    }


    fun setDarkStatusIcon(isDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val decorView = window.decorView
            if (decorView != null) {
                var visibility = decorView.systemUiVisibility
                visibility
                decorView.systemUiVisibility = if (isDark) {
                    //设置黑色状态栏图标
                    visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    //设置白色状态栏图标
                    visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
            }
        } else {
            try {
                val setDarkStatusIcon = Window::class.java.getDeclaredMethod("setDarkStatusIcon",
                        Boolean::class.javaPrimitiveType)
                setDarkStatusIcon.invoke(window, isDark)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun setStatusBarColor(colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(colorId)
        }
    }

    fun setStatusBarColor(color: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor(color)
        }
    }

    open fun initViews() {}

    fun forcedExitOnBackPressed() {
        Logger.d()
        if (System.currentTimeMillis() - firstPressedTime < ON_BACK_EXIT_DURATION) {
            Logger.d("Exit app")
            ActivityCollector.finishAll()
        } else {
            toast(R.string.back_again_exit)
            firstPressedTime = System.currentTimeMillis()
        }
    }

    companion object {
        private const val ON_BACK_EXIT_DURATION = 2000
    }
}