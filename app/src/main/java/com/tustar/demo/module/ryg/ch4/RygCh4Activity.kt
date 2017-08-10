package com.tustar.demo.module.ryg.ch4

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch3.RygCh3InnerActivity
import com.tustar.demo.module.ryg.ch3.RygCh3OuterActivity
import com.tustar.demo.module.ryg.ch3.RygCh3ViewMoveActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_ryg_ch4.*

class RygCh4Activity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = RygCh4Activity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()

        init {
            sClassList.add(RygCh4WidthHeightActivity::class.java)
            sClassList.add(RygCh4CircleViewActivity::class.java)
            sClassList.add(RygCh4ExActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch4)
        title = getString(R.string.ryg_ch4_title)

        ryg_ch4_rv.layoutManager = LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.ryg_ch4_list).toList()
        var adapter = SimpleListItem1Adapter(desc)
        ryg_ch4_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        ryg_ch4_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
