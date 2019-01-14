package com.tustar.demo.ui.jet

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tustar.common.util.Logger
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.ui.fm.service.FileMonitorService
import com.tustar.demo.ui.jet.pagingroom.BookActivity
import kotlinx.android.synthetic.main.activity_jet_main.*

class JetMainActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = JetMainActivity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                add(BookActivity::class.java)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jet_main)
        title = getString(R.string.jet_main_title)

        jet_main_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
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