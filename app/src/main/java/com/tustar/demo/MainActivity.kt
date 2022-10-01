package com.tustar.demo

import android.os.Bundle
import com.tustar.annotation.Sample
import com.tustar.ui.BaseActivity

@Sample(
    group = "group_custom_widget",
    item = "custom_composes_example",
    createdAt = "2022-08-25 10:15:00",
    updatedAt = "2021-08-25 15:24:00",
)
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment.newInstance())
                .commitNow()
        }
    }
}