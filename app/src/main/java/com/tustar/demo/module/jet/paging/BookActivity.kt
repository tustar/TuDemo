package com.tustar.demo.module.jet.paging

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class BookActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jet_book)
        title = getString(R.string.jet_book_title)

        // 注册需要监听的Observer
        lifecycle.addObserver(ActivityLifeObserver())
    }
}
