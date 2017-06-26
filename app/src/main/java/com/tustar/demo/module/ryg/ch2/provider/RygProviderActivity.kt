package com.tustar.demo.module.ryg.ch2.provider

import android.content.ContentValues
import android.os.Bundle
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch2.model.Book
import com.tustar.demo.module.ryg.ch2.model.User
import com.tustar.demo.util.Logger


class RygProviderActivity : BaseActivity() {

    companion object {
        private var TAG = RygProviderActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Logger.i(TAG, "onCreate ::")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_provider)
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
        }
        userCursor.close()
    }
}
