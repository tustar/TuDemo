package com.tustar.demo.module.fm

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.common.CommonDefine
import com.tustar.demo.module.fm.service.FileMonitorService
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_fm_main.*

class FmMainActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = FmMainActivity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                add(FmGifPlayerActivity::class.java)
                add(FmGifDrawableActivity::class.java)
                add(FmRenameActivity::class.java)
                add(FmListViewXActivity::class.java)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fm_main)
        title = getString(R.string.fm_main_title)

        fm_main_rv.layoutManager = LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.fm_list).toList()
        var adapter = SimpleListItem1Adapter(desc)
        fm_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        fm_main_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))

        // 监听文件变化
        FileMonitorService.startMonitor(this)
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        if (clazz == FmGifPlayerActivity::class.java) {
            intent.putExtra(FmGifPlayerActivity.GIF_FILE_PATH,
                    CommonDefine.TEST_GIF)
        }
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
