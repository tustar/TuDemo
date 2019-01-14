package com.tustar.demo.ui.fm

import android.os.Bundle
import com.tustar.common.util.ToastUtils
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.common.CommonDefine
import kotlinx.android.synthetic.main.activity_fm_gif_drawable.*
import pl.droidsonroids.gif.GifDrawable
import java.io.File
import java.io.IOException

class FmGifDrawableActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_gif_drawable)
        title = getString(R.string.fm_android_gif_drawable)

        var drawable: GifDrawable? = null
        try {
            if (File(CommonDefine.TEST_GIF).exists()) {
                drawable = GifDrawable(CommonDefine.TEST_GIF)
            } else {
                ToastUtils.showLong(this, R.string.image_no_exist)
                drawable = GifDrawable(assets, "gif_demo")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        gif_image_view.setImageDrawable(drawable)
    }
}
