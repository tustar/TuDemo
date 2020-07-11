package com.tustar.ryg

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tustar.ryg.ch1.RygCh1Activity
import com.tustar.ryg.ch10.RygCh10Activity
import com.tustar.ryg.ch11.RygCh11Activity
import com.tustar.ryg.ch12.RygCh12Activity
import com.tustar.ryg.ch13.RygCh13Activity
import com.tustar.ryg.ch14.RygCh14Activity
import com.tustar.ryg.ch15.RygCh15Activity
import com.tustar.ryg.ch2.RygCh2Activity
import com.tustar.ryg.ch3.RygCh3Activity
import com.tustar.ryg.ch4.RygCh4Activity
import com.tustar.ryg.ch5.RygCh5Activity
import com.tustar.ryg.ch6.RygCh6Activity
import com.tustar.ryg.ch7.RygCh7Activity
import com.tustar.ryg.ch8.RygCh8Activity
import com.tustar.ryg.ch9.RygCh9Activity
import com.tustar.util.Logger
import com.tustar.widget.Decoration
import kotlinx.android.synthetic.main.activity_ryg_main.*

class RygMainActivity : AppCompatActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = RygMainActivity::class.simpleName
        private val sClassList = ArrayList<Class<*>>()

        // List
        init {
            sClassList.run {
                add(RygCh1Activity::class.java)
                add(RygCh2Activity::class.java)
                add(RygCh3Activity::class.java)
                add(RygCh4Activity::class.java)
                add(RygCh5Activity::class.java)
                add(RygCh6Activity::class.java)
                add(RygCh7Activity::class.java)
                add(RygCh8Activity::class.java)
                add(RygCh9Activity::class.java)
                add(RygCh10Activity::class.java)
                add(RygCh11Activity::class.java)
                add(RygCh12Activity::class.java)
                add(RygCh13Activity::class.java)
                add(RygCh14Activity::class.java)
                add(RygCh15Activity::class.java)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate ::")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_main)
        title = getString(R.string.ryg_main_title)

        ryg_main_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        var desc = resources.getStringArray(R.array.ryg_chapters).toList()
        var adapter = SimpleListItem1Adapter(desc)
        ryg_main_rv.adapter = adapter
        adapter.setOnItemClickListener(this)
        ryg_main_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }
}