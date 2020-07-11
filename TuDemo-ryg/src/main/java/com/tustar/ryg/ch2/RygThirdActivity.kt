package com.tustar.ryg.ch2

import androidx.appcompat.app.AppCompatActivity
import com.tustar.ryg.R

class RygThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_third)
        title = getString(R.string.ryg_ch2_third)
    }
}
