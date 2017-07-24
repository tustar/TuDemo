package com.tustar.demo.module.ryg.ch2.aidl

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import com.tustar.demo.util.Logger
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by tustar on 17-7-3.
 */
class BookManagerService : Service() {

    companion object {
        private val TAG = BookManagerService::class.java.simpleName
        private val ACCESS_BOOK_SERVICE = "com.tustar.permission.ACCESS_BOOK_SERVICE"
    }

    private var mIsServiceDestroyed = AtomicBoolean(false)
    private var mBookList = CopyOnWriteArrayList<Book>()
    private var mListenerList = RemoteCallbackList<IOnNewBookArrivedListener>()
    private var mBinder = object : IBookManager.Stub() {
        override fun getBookList(): MutableList<Book> {
//            SystemClock.sleep(5000)
            return mBookList
        }

        override fun addBook(book: Book?) {
            mBookList.add(book)
        }

        override fun registerListener(listener: IOnNewBookArrivedListener?) {
            mListenerList.register(listener)
            val N = mListenerList.beginBroadcast()
            mListenerList.finishBroadcast()
            Logger.d(TAG, "registerListener, current size: ${N}")
        }

        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
            var success = mListenerList.unregister(listener)
            if (success) {
                Logger.d(TAG, "unregister success.");
            } else {
                Logger.d(TAG, "not found, can not unregister.");
            }
            val N = mListenerList.beginBroadcast()
            mListenerList.finishBroadcast()
            Logger.d(TAG, "registerListener, current size: ${N}")
        }

        override fun onTransact(code: Int, data: Parcel?, reply: Parcel?, flags: Int): Boolean {
            if (checkCallingOrSelfPermission(ACCESS_BOOK_SERVICE) ==
                    PackageManager.PERMISSION_DENIED) {
                return false
            }

            if (!packageManager.getPackagesForUid(Binder.getCallingUid())[0]
                    .startsWith("com.tustar")) {
                return false
            }

            return super.onTransact(code, data, reply, flags)
        }
    }

    override fun onCreate() {
        Logger.i(TAG, "onCreate :: ")
        super.onCreate()
        mBookList.add(Book(1, "Android"))
        mBookList.add(Book(2, "IOS"))
        Thread(ServiceWorker()).start()
    }

    override fun onBind(intent: Intent?): IBinder? {
        if (checkCallingOrSelfPermission(ACCESS_BOOK_SERVICE) ==
                PackageManager.PERMISSION_DENIED) {
            return null
        }

        return mBinder
    }

    override fun onDestroy() {
        mIsServiceDestroyed.set(true)
        super.onDestroy()
    }

    inner class ServiceWorker : Runnable {
        override fun run() {
            while (!mIsServiceDestroyed.get()) {
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
    private fun onNewBookArrived(book: Book) {
        mBookList.add(book)
        val N = mListenerList.beginBroadcast()
        (0 until N).forEach {
            var l = mListenerList.getBroadcastItem(it)
            if (l != null) {
                l.onNewBookArrived(book)
            }
        }
        mListenerList.finishBroadcast()
    }
}