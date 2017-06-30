package com.tustar.demo.module.ryg

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch2.RygMainActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_ryg_art_main.*
import java.util.*
import kotlin.collections.ArrayList

class RygArtMainActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = RygArtMainActivity::class.simpleName
        private val sClassList = ArrayList<Class<*>>()
        private val sDescList = ArrayList<String>()

        // List
        init {
            sClassList.add(RygMainActivity::class.java)
            sDescList.add("Chapter02")
            //
            Collections.reverse(sClassList)
            Collections.reverse(sDescList)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate ::")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_art_main)

        ryg_art_main.layoutManager = LinearLayoutManager(this)
        var adapter = SimpleListItem1Adapter(sDescList)
        Logger.d(TAG, "onCreate :: " + sDescList)
        ryg_art_main.adapter = adapter
        adapter!!.setOnItemClickListener(this)
        ryg_art_main.addItemDecoration(Decoration(this, Decoration.VERTICAL ))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        if (null != clazz) {
            intent.setClass(this, clazz)
            startActivity(intent)
        }
    }
}
