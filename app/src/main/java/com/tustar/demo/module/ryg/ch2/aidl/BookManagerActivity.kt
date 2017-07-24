package com.tustar.demo.module.ryg.ch2.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.widget.LinearLayoutManager
import com.tustar.demo.R
import com.tustar.demo.adapter.SimpleListItem1Adapter
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.util.Logger
import com.tustar.demo.util.ToastUtils
import com.tustar.demo.widget.Decoration
import kotlinx.android.synthetic.main.activity_ryg_book_manager.*


class BookManagerActivity : BaseActivity() {

    companion object {
        private val TAG = BookManagerActivity::class.java.simpleName
        private val MESSAGE_NEW_BOOK_ARRIVED = 1
    }

    private var mRemoteBookManager: IBookManager? = null
    private var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_NEW_BOOK_ARRIVED -> {
                    Logger.d(TAG, "handleMessage :: receive new book: ${msg.obj}")
                    updateData()
                }
                else -> super.handleMessage(msg)
            }

        }
    }

    private var mOnNewBookArrivedListener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(book: Book?) {
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget()
        }
    }

    private var mDeathRecipient: IBinder.DeathRecipient? = null
    init {
        mDeathRecipient = IBinder.DeathRecipient {
            Logger.d(TAG, "binder died. tname:${Thread.currentThread().name}")
            mRemoteBookManager!!.asBinder().unlinkToDeath(mDeathRecipient, 0)
            mRemoteBookManager = null
            // TODO:这里重新绑定远程Service
        }
    }


    private var mData = emptyList<String>()
    private var mAdapter: SimpleListItem1Adapter? = null
    private var mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            var bookManager = IBookManager.Stub.asInterface(service)
            mRemoteBookManager = bookManager
            try {
                mRemoteBookManager!!.asBinder().linkToDeath(mDeathRecipient, 0)
                var list = bookManager.bookList
                Logger.i(TAG, "onServiceConnected :: query book list ${list}")
                var book = Book(3, "Android开发艺术探索")
                Logger.i(TAG, "onServiceConnected :: add book: ${book}")
                bookManager.addBook(book)
                updateData()
                Logger.i(TAG, "onServiceConnected :: query book list, list type: " +
                        "${list::class.java.canonicalName}")
                bookManager.registerListener(mOnNewBookArrivedListener)

            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mRemoteBookManager = null
            Logger.d(TAG, "onServiceDisconnected :: binder died.")
        }
    }

    private fun updateData() {
        var newList = mRemoteBookManager!!.bookList
        runOnUiThread {
            mData = newList.map { it -> it.toString() }
            mAdapter!!.data = mData
            mAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_book_manager)
        title = getString(R.string.ryg_ch2_book_manager)

        var intent = Intent(this, BookManagerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)

        initView()
    }

    private fun initView() {
        ryg_ch2_bm_rv.layoutManager = LinearLayoutManager(this)
        mAdapter = SimpleListItem1Adapter(mData)
        ryg_ch2_bm_rv.adapter = mAdapter
        ryg_ch2_bm_rv.addItemDecoration(Decoration(this, Decoration.VERTICAL))

        ryg_ch2_bm_btn.setOnClickListener {
            Logger.d(TAG, "setOnClickListener ::")
            ToastUtils.showLong(this, "Get Book List")
            Thread({ updateData() }).start()
        }
    }

    override fun onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager!!.asBinder().isBinderAlive) {
            try {
                Logger.i(TAG, "onDestroy :: unregister listener: ${mOnNewBookArrivedListener}")
                mRemoteBookManager!!.unregisterListener(mOnNewBookArrivedListener)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        unbindService(mConnection)
        super.onDestroy()
    }
}
