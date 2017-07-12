package com.tustar.demo.module.ryg.ch2.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import com.tustar.demo.util.Logger

import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by tustar on 17-7-3.
 */
class BookManagerService : Service() {

    companion object {
        private val TAG = BookManagerService::class.java.simpleName
    }

    private var mIsServiceDestoryed = AtomicBoolean(false)
    private var mBookList = CopyOnWriteArrayList<Book>()
    private var mListenerList = CopyOnWriteArrayList<IOnNewBookArrivedListener>()
    private var mBinder = object : IBookManager.Stub() {
        override fun getBookList(): MutableList<Book> {
            return mBookList
        }

        override fun addBook(book: Book?) {
            mBookList.add(book)
        }

        override fun registerListener(listener: IOnNewBookArrivedListener?) {
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener)
            } else {
                Logger.d(TAG, "registerListener :: already exists.")
            }
            Logger.d(TAG, "registerListener :: current size: ${mListenerList.size}")
        }

        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
            if (mListenerList.contains(listener)) {
                mListenerList.remove(listener)
                Logger.d(TAG, "unregisterListener :: unregister success")
            } else {
                Logger.d(TAG, "unregisterListener :: no found, can not unregister")
            }
            Logger.d(TAG, "unregisterListener :: current size: ${mListenerList.size}")
        }
    }


    override fun onCreate() {
        super.onCreate()
        mBookList.add(Book(1, "Android"))
        mBookList.add(Book(2, "IOS"))
        Thread(ServiceWorker()).start()
    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    inner class ServiceWorker : Runnable {
        override fun run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                var bookId = mBookList.size + 1
                var newBook = Book(bookId, "new book#${bookId}")
                try {
                    onNewBookArrived(newBook)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @Throws(RemoteException::class)
    private fun  onNewBookArrived(book: Book) {
        mBookList.add(book)
        Logger.d(TAG, "onNewBookArrived :: notify listeners: ${mBookList.size}")
        mListenerList.forEach { it ->
            Logger.d(TAG, "onNewBookArrived :: notify listener: ${it}")
            it.onNewBookArrived(book)
        }
    }


//    @Throws(RemoteException::class)
//    private fun onNewBookArrived(book: Book) {
//        mBookList.add(book)
//        val N = mListenerList.beginBroadcast()
//        for (i in 0..N - 1) {
//            val l = mListenerList.getBroadcastItem(i)
//            if (l != null) {
//                try {
//                    l!!.onNewBookArrived(book)
//                } catch (e: RemoteException) {
//                    e.printStackTrace()
//                }
//
//            }
//        }
//        mListenerList.finishBroadcast()
//    }
}