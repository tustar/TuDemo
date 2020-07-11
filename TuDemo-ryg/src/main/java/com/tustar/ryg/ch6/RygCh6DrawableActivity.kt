package com.tustar.ryg.ch6

import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ScaleDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import com.tustar.ryg.R
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ryg_ch6_drawable.*

class RygCh6DrawableActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch6_drawable)
        title = getString(R.string.ryg_ch6_drawable)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            (ryg_ch6_transition.background as TransitionDrawable).startTransition(1000)

            (ryg_ch6_scale.background as ScaleDrawable).level = 10

            (ryg_ch6_clip.drawable as ClipDrawable).level = 8000

            ryg_ch6_custom.background = CustomDrawable(Color.parseColor("#0ac39e"))
        }
    }
}
