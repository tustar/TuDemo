package com.tustar.jetpack.pagingroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tustar.demo.ui.jet.pagingroom.ActivityLifeObserver
import com.tustar.jetpack.R

class PagingRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagingroom)
        title = getString(R.string.jet_book_title)

        // 注册需要监听的Observer
        lifecycle.addObserver(ActivityLifeObserver())
    }
}
