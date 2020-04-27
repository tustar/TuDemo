package com.tustar.fl

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tustar.util.Logger
import com.tustar.widget.Decoration
import kotlinx.android.synthetic.main.activity_book_base.*

open class BaseBookActivity : AppCompatActivity(), SimpleListItem1Adapter.OnItemClickListener {

    companion object {
        private val TAG = BaseBookActivity::class.java.simpleName
    }

    open val sClassList = ArrayList<Class<*>>()
    open var book_data_source: Int = -1
    open var book_layout_id = R.layout.activity_book_base
    private var mAdapter: SimpleListItem1Adapter? = null
    lateinit var mDataObserver: RecyclerView.AdapterDataObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(book_layout_id)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        if (book_data_source == -1) {
            showEmpytView(true)
            return
        }
        showEmpytView(false)

        book_base_rv.layoutManager = LinearLayoutManager(this)
        var desc = resources.getStringArray(book_data_source).toList()
        mAdapter = SimpleListItem1Adapter(desc)
        mDataObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                showEmpytView(mAdapter!!.itemCount == 0)
            }
        }
        mAdapter!!.setOnItemClickListener(this)
        mAdapter!!.registerAdapterDataObserver(mDataObserver)
        book_base_rv.adapter = mAdapter

        book_base_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
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
            book_base_empty_view.visibility = View.VISIBLE
            book_base_rv.visibility = View.GONE
        } else {
            book_base_empty_view.visibility = View.GONE
            book_base_rv.visibility = View.VISIBLE
        }
    }
}