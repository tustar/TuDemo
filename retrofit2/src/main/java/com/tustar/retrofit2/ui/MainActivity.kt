package com.tustar.retrofit2.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tustar.retrofit2.R
import com.tustar.retrofit2.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit2_main)
    }


    override fun onResume() {
        super.onResume()

        val intent = Intent(this, LoginActivity::class.java).apply {

        }
        startActivity(intent)
    }
}
