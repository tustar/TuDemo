package com.tustar.demo.module.ryg.ch2.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import com.tustar.demo.R
import com.tustar.demo.base.BaseActivity
import com.tustar.demo.module.ryg.ch2.utils.MyConstants
import com.tustar.common.util.Logger


class MessengerActivity : BaseActivity() {

    companion object {
        private val TAG = MessengerActivity::class.java.simpleName
    }

    private var mService: Messenger? = null
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
            Logger.d(TAG, "bind service")
            var msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT)
            var data = Bundle()
            data.putString("msg", "hello, this is client.")
            msg.data = data
            msg.replyTo = mGetReplyMessenger
            try {
                mService!!.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MyConstants.MSG_FROM_SERVICE -> {
                    Logger.i(TAG, "receive msg from Client: ${msg.data.getString("reply")}")
                }
                else -> super.handleMessage(msg)
            }
        }
    }
    private var mGetReplyMessenger = Messenger(MessengerHandler())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ryg_messenger)
        title = getString(R.string.ryg_ch2_messenger)

        var intent = Intent(this, MessengerService::class.java)
        intent.action = "com.tustar.MessengerService.launch"
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(mConnection)
        super.onDestroy()
    }
}
