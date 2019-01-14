package com.tustar.demo.ui.fl

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.tustar.common.util.Logger
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.ui.fl.ch1.FlCh1Activity
import com.tustar.demo.ui.fl.ch2.FlCh2Activity
import com.tustar.demo.ui.fl.ch3.FlCh3Activity
import com.tustar.demo.ui.fl.ch4.FlCh4Activity
import com.tustar.demo.ui.fl.ch5.FlCh5Activity
import kotlinx.android.synthetic.main.activity_fl_main.*

class FlMainActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = FlMainActivity::class.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                add(FlCh1Activity::class.java)
                add(FlCh2Activity::class.java)
                add(FlCh3Activity::class.java)
                add(FlCh4Activity::class.java)
                add(FlCh5Activity::class.java)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate ::")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fl_main)
        title = getString(R.string.fl_main_title)

        fl_main_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.fl_chapters).toList()
        var adapter = SimpleListItem1Adapter(desc)
        fl_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        fl_main_rv.addItemDecoration(com.tustar.common.widget.Decoration(this, com.tustar.common.widget.Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
