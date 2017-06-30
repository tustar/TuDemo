package com.tustar.demo.module.ryg.ch2.messenger

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by tustar on 17-6-30.
 */
class MessengerService: Service() {

    private val TAG = MessengerService::class.java.simpleName



    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}