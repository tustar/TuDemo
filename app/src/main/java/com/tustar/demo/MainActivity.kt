package com.tustar.demo

import android.os.Bundle
import android.view.LayoutInflater
import com.tustar.demo.databinding.ActivityMainBinding
import com.tustar.ui.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override val bindingInflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun initViews() {

    }

    override fun initData() {
    }
}