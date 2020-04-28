package com.tustar.fm

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import com.tustar.util.ToastUtils
import com.tustar.fm.R
import com.tustar.fm.BaseActivity
import kotlinx.android.synthetic.main.activity_fm_sp_share.*

class FmSpShareActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_sp_share)
        setTitle(R.string.fm_sp_share)

        fm_sp_share_commit.setOnClickListener {
            val text = fm_sp_share_edit.text.trim()
            if (TextUtils.isEmpty(text)) {
                ToastUtils.showShort(FmSpShareActivity@ this, "The content is empty")
                return@setOnClickListener
            }

            val sp = getSharedPreferences("sp_share", Context.MODE_WORLD_READABLE)
            val editor = sp.edit()
            editor.putString("content", text.toString())
            editor.commit()
        }
    }
}
