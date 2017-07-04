package com.tustar.demo.module.gif


import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.util.ToastUtils
import kotlinx.android.synthetic.main.activity_gif_player.*
import java.io.File


class GifPlayerActivity : BaseActivity() {

    companion object {
        private val TAG = "GifPlayerActivity"
        val GIF_FILE_PATH = "com.zui.filemanager.gif_file_path"
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate :: ")
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif_player)
        title = getString(R.string.gif_player)

        val mFilePath = intent.getStringExtra(GIF_FILE_PATH)
        gif_view.setOnClickListener {
            finish()
        }
        gif_view.setDecodeListener(object : GifView.DecodeListener {
            override fun onDecodeStart() {
                Logger.i(TAG, "onDecodeStart :: ")
                gif_view.visibility = View.INVISIBLE
                gif_progress.visibility = View.VISIBLE
            }

            override fun onDecodeEnd(result: Boolean) {
                Logger.d(TAG, "onDecodeEnd :: ")
                gif_view.visibility = View.VISIBLE
                gif_progress.visibility = View.GONE
            }
        })
        if (File(mFilePath).exists()) {
            gif_view.setGifSource(mFilePath)
        } else {
            ToastUtils.showLong(this, R.string.image_no_exist)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
