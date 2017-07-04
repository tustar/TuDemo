package com.tustar.demo.module.gif

import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.common.CommonDefine
import com.tustar.demo.util.ToastUtils
import kotlinx.android.synthetic.main.activity_gif_drawable.*
import pl.droidsonroids.gif.GifDrawable
import java.io.File
import java.io.IOException

class GifDrawableActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif_drawable)
        title = getString(R.string.gif_android_gif_drawable)

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
