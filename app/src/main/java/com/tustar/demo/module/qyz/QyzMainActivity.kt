package com.tustar.demo.module.qyz

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_qyz_main.*

class QyzMainActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = QyzMainActivity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                sClassList.add(QyzRotationActivity::class.java)
                sClassList.add(QyzPaletteActivity::class.java)
                sClassList.add(QyzTintingActivity::class.java)
                sClassList.add(QyzElevationActivity::class.java)
                sClassList.add(QyzClippingActivity::class.java)
                sClassList.add(QyzAnimMainActivity::class.java)
                sClassList.add(QyzSurfaceViewActivity::class.java)
                sClassList.add(QyzSvgActivity::class.java)
                sClassList.add(QyzFlexibleListViewActivity::class.java)
                sClassList.add(QyzOverScrollGridViewActivity::class.java)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qyz_main)
        title = getString(R.string.qyz_main_title)

        qyz_main_rv.layoutManager = LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.qyz_list).toList()
        var adapter = SimpleListItem1Adapter(desc)
        qyz_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        qyz_main_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}