package com.tustar.demo.module.ryg.ch2

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch2.messenger.MessengerActivity
import com.tustar.demo.module.ryg.ch2.provider.RygProviderActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_ryg_ch2.*

class RygCh2Activity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = RygCh2Activity::class.java.simpleName
        private val sClassList = ArrayList<Class<*>>()
        private val sDescList = ArrayList<String>()

        init {
            //
            sClassList.add(RygOneActivity::class.java)
            sDescList.add("Android IPC")
            //
            sClassList.add(RygProviderActivity::class.java)
            sDescList.add("Provider")
            // 
            sClassList.add(MessengerActivity::class.java)
            sDescList.add("Messenger")
            //
            sClassList.reversed()
            sDescList.reversed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_ch2)
        title = getString(R.string.ryg_ch2_title)

        ryg_ch2_rv.layoutManager = LinearLayoutManager(this)
        var adapter = SimpleListItem1Adapter(sDescList)
        Logger.d(TAG, "onCreate :: " + sDescList)
        ryg_ch2_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        ryg_ch2_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}
