package com.tustar.demo.module.ryg.ch5

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_ryg_base.*

open class BaseRygActivity : BaseActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = BaseRygActivity::class.java.simpleName
    }

    open val sClassList = ArrayList<Class<*>>()
    open var ryg_data_source: Int = -1
    private var mAdapter: SimpleListItem1Adapter? = null
    private var mDataObserver: RecyclerView.AdapterDataObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_base)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        if (ryg_data_source == -1) {
            showEmpytView(true)
            return
        }
        showEmpytView(false)

        ryg_base_rv.layoutManager = LinearLayoutManager(this)
        var desc = resources.getStringArray(ryg_data_source).toList()
        mAdapter = SimpleListItem1Adapter(desc)
        mDataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                showEmpytView(mAdapter!!.itemCount == 0)
            }
        }
        mAdapter!!.setOnItemClickListener(this)
        mAdapter!!.registerAdapterDataObserver(mDataObserver)
        ryg_base_rv.adapter = mAdapter

        ryg_base_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }

    override fun onItemClick(view: View, position: Int) {
        Logger.i(TAG, "onItemClick :: view = $view, position = $position")
        val intent = Intent()
        val clazz = sClassList[position]
        intent.setClass(this, clazz)
        startActivity(intent)
    }

    override fun onDestroy() {
        if (mAdapter != null && mDataObserver != null) {
            mAdapter!!.unregisterAdapterDataObserver(mDataObserver)
        }
        super.onDestroy()
    }

    private fun showEmpytView(visibility: Boolean) {
        if (visibility) {
            rgy_base_empty_view.visibility = View.VISIBLE
            ryg_base_rv.visibility = View.GONE
        } else {
            rgy_base_empty_view.visibility = View.GONE
            ryg_base_rv.visibility = View.VISIBLE
        }
    }
}