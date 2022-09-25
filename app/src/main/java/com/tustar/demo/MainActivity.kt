package com.tustar.demo

import android.os.Bundle
import android.view.LayoutInflater
import com.tustar.annotation.Sample
import com.tustar.demo.databinding.ActivityMainBinding
import com.tustar.ui.BaseActivity

@Sample(
    group = "group_custom_widget",
    item = "custom_composes_example",
    createdAt = "2022-08-25 10:15:00",
    updatedAt = "2021-08-25 15:24:00",
)
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