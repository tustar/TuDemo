package com.tustar.demo.module.ryg.ch2.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder

import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by tustar on 17-7-3.
 */
class BookManagerService : Service() {

    companion object {
        private val TAG = BookManagerService::class.java.simpleName
    }

    private var mBookList = CopyOnWriteArrayList<Book>()
    private var mBinder = object : IBookManager.Stub() {
        override fun getBookList(): MutableList<Book> {
            return mBookList
        }

        override fun addBook(book: Book?) {
            mBookList.add(book)
        }
    }

    override fun onCreate() {
        super.onCreate()
        mBookList.add(Book(1, "Android"))
        mBookList.add(Book(2, "IOS"))
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }
}