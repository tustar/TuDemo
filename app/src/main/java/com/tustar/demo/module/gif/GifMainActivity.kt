package com.tustar.demo.module.gif

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.common.CommonDefine
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_gif_main.*

class GifMainActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = GifMainActivity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                add(GifPlayerActivity::class.java)
                add(GifDrawableActivity::class.java)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif_main)
        title = getString(R.string.gif_main_title)

        qyz_main_rv.layoutManager = LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.gif_list).toList()
        var adapter = SimpleListItem1Adapter(desc)
        qyz_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        qyz_main_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        if (clazz == GifPlayerActivity::class.java) {
            intent.putExtra(GifPlayerActivity.GIF_FILE_PATH,
                    CommonDefine.TEST_GIF)
        }
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
