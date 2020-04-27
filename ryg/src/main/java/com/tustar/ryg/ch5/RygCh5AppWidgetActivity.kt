package com.tustar.ryg.ch5

import android.os.Bundle
import com.tustar.ryg.R
import androidx.appcompat.app.AppCompatActivity

class RygCh5AppWidgetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch5_app_widget)
        title = getString(R.string.ryg_ch5_app_widget)
    }
}
