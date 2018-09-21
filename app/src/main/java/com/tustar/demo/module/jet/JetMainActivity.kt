package com.tustar.demo.module.jet

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tustar.common.util.Logger
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.fm.service.FileMonitorService
import com.tustar.demo.module.jet.paging.BookActivity
import com.tustar.demo.module.jet.reddit.RedditMainActivity
import kotlinx.android.synthetic.main.activity_jet_main.*

class JetMainActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = JetMainActivity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                add(BookActivity::class.java)
                add(RedditMainActivity::class.java)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jet_main)
        title = getString(R.string.jet_main_title)

        jet_main_rv.layoutManager = LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.jet_list).toList()
        var adapter = SimpleListItem1Adapter(desc)
        jet_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        jet_main_rv.addItemDecoration(com.tustar.common.widget.Decoration(this, com.tustar.common.widget.Decoration.VERTICAL))

        // 监听文件变化
        FileMonitorService.startMonitor(this)
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
