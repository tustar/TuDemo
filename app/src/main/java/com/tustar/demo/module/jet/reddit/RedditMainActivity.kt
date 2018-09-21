package com.tustar.demo.module.jet.reddit

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity

class RedditMainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jet_reddit_main)
        title = getString(R.string.jet_reddit_title)
    }
}
