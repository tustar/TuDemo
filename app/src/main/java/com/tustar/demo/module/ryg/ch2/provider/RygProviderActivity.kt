package com.tustar.demo.module.ryg.ch2.provider

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch2.model.Book
import com.tustar.demo.module.ryg.ch2.model.User
import com.tustar.demo.util.Logger
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_ryg_provider.*


class RygProviderActivity : BaseActivity() {

    companion object {
        private var TAG = RygProviderActivity::class.simpleName
    }

    private var mData = ArrayList<String>()
    private var mAdapter: SimpleListItem1Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate ::")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_provider)
        title = getString(R.string.ryg_ch2_provider)

        initView()

        val values = ContentValues()
        values.put("_id", 6)
        values.put("name", "程序设计的艺术")
        contentResolver.insert(BookProvider.BOOK_CONTENT_URI, values)
        val bookCursor = contentResolver.query(BookProvider.BOOK_CONTENT_URI,
                arrayOf("_id", "name"), null, null, null)
        while (bookCursor!!.moveToNext()) {
            val book = Book()
            book.id = bookCursor.getInt(0)
            book.name = bookCursor.getString(1)
            Logger.d(TAG, "query book:" + book.toString())
            mData.add(book.toString())
        }
        bookCursor.close()

        val userCursor = contentResolver.query(BookProvider.USER_CONTENT_URI,
                arrayOf("_id", "name", "sex"), null, null, null)
        while (userCursor!!.moveToNext()) {
            val user = User()
            user.id = userCursor.getInt(0)
            user.name = userCursor.getString(1)
            user.isMale = userCursor.getInt(2) === 1
            Logger.d(TAG, "query user:" + user.toString())
            mData.add(user.toString())
        }
        userCursor.close()
        mAdapter!!.data = mData
        mAdapter!!.notifyDataSetChanged()
    }


    private fun initView() {
        ryg_ch2_provider_rv.layoutManager = LinearLayoutManager(this)
        mAdapter = SimpleListItem1Adapter(mData)
        ryg_ch2_provider_rv.adapter = mAdapter
        ryg_ch2_provider_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))
    }
}
