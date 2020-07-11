package com.tustar.fm

import android.os.Bundle
import com.tustar.fm.R
import com.tustar.fm.BaseActivity
import com.tustar.util.ToastUtils
import kotlinx.android.synthetic.main.activity_fm_gif_drawable.*
import pl.droidsonroids.gif.GifDrawable
import java.io.File
import java.io.IOException

const val TEST_GIF: String = "/storage/emulated/0/img-9d0d09ably1fgslbs4vu3g20b205z7wm.gif"

class FmGifDrawableActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_gif_drawable)
        title = getString(R.string.fm_android_gif_drawable)

        var drawable: GifDrawable? = null
        try {
            drawable = if (File(TEST_GIF).exists()) {
                GifDrawable(TEST_GIF)
            } else {
                ToastUtils.showLong(this, R.string.image_no_exist)
                GifDrawable(assets, "gif_demo")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        gif_image_view.setImageDrawable(drawable)
    }
}
