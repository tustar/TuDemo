package com.tustar.demo.module.ryg.ch2.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import com.tustar.demo.module.ryg.ch2.utils.MyConstants
import com.tustar.common.util.Logger


/**
 * Created by tustar on 17-6-30.
 */
class MessengerService : Service() {

    companion object {
        private val TAG = MessengerService::class.java.simpleName
    }

    private class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MyConstants.MSG_FROM_CLIENT -> {
                    Logger.i(TAG, "receive msg from Client: ${msg.data.getString("msg")}")
                    var client = msg.replyTo
                    var replyMsg = Message.obtain(null, MyConstants.MSG_FROM_SERVICE)
                    var data = Bundle()
                    data.putString("reply", "嗯，你的消息我已经收到，稍后会回复你")
                    replyMsg.data = data
                    try {
                        client.send(replyMsg)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private val mMessenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent?): IBinder {
        return mMessenger.binder
    }
}